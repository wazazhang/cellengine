package com.net.chat;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Vector;

import com.cell.io.NIODeserialize;
import com.cell.io.NIOSerialize;
import com.cell.util.EnumManager;
import com.net.MessageHeader;




public class ChatMessages
{
//	-----------------------------------------------------------------------------------------------------------------------------
	
	final static public String CHARSET = "UTF-8";
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 请求登录到服务器。
	 */
	public static class LoginRequestC2S extends MessageHeader
	{
		private static final long serialVersionUID = 1L;

		public static LoginRequestC2S create_Login(String user, String pwd) {
			LoginRequestC2S login = new LoginRequestC2S();
			login.UserName 	= user;
			login.UserPWD 	= pwd;
			return login;
		}

		public String UserName;
		public String UserPWD ;
		
		private LoginRequestC2S() {}
		
		public String toString() {
			return "LoginRequestC2S [" + UserName + "," + UserPWD + /*"," + World +  "," + RealmX + "," + RealmY + */"]"; 
		}
	}
	
	
	/**
	 * 服务器接受用户登录后，
	 * 发送成功于否和默认的聊天频道
	 */
	public static class LoginResponseS2C extends MessageHeader
	{
		private static final long serialVersionUID = 1L;

		public static enum EResult
		{
			SUCCEED(),
			FAILED(),
			AUTHORITY_ERROR(),
			;
		}

		public static LoginResponseS2C create_SUCCEED() {
			LoginResponseS2C ret = new LoginResponseS2C();
			ret.Result = EResult.SUCCEED;
			return ret;
		}
		
		public static LoginResponseS2C create_FAILED(String reason) {
			LoginResponseS2C ret = new LoginResponseS2C();
			ret.Result = EResult.FAILED;
			ret.Reason = reason;
			return ret;
		}
		
		public static LoginResponseS2C create_AUTHORITY_ERROR(String reason) {
			LoginResponseS2C ret = new LoginResponseS2C();
			ret.Result = EResult.AUTHORITY_ERROR;
			ret.Reason = reason; 
			return ret;
		}
		

		////////////////////////////////////////////////////


		public EResult Result;
		public String Reason;
		
		private LoginResponseS2C() {}
		
		public String toString() {
			return "LoginResponseS2C [" + 
				Result + "," + 
				Reason + "," +
				"]"; 
		}
	}
	
	/**
	 * 发送聊天
	 * @author waza
	 */
	public static class ChatRequestC2S extends MessageHeader
	{
		private static final long serialVersionUID = 1L;

		public static enum EAction
		{
			ALL(),
			CHANNEL(),
			SINGLE(),
			;
		}
		
		public static ChatRequestC2S create_ChatAll(String sender, String message) {
			ChatRequestC2S req = new ChatRequestC2S(EAction.ALL);
			req.Sender = sender;
			req.Message = message;
			return req;
		}
		
		public static ChatRequestC2S create_ChatChannel(String sender, int channel, String message) {
			ChatRequestC2S req = new ChatRequestC2S(EAction.CHANNEL);
			req.Sender = sender;
			req.Channel = channel;
			req.Message = message;
			return req;
		}
		
		public static ChatRequestC2S create_ChatSingle(String sender, String reciver, String message) {
			ChatRequestC2S req = new ChatRequestC2S(EAction.SINGLE);
			req.Sender = sender;
			req.Reciver = reciver;
			req.Message = message;
			return req;
		}
		

		////////////////////////////////////////////////////

		public EAction Action;
		public String Sender;
		public String Reciver;
		public int Channel;
		public String Message;
		
		private ChatRequestC2S(EAction action) {
			this.Action = action;
		}
		
		public String toString() {
			return "ChatRequestC2S [" + 
			Sender + "," + 
			Reciver + "," +
			Channel + "," + 
			Message + "," +
			"]" ; 
		}
	}
	
	/**
	 * 服务器回馈聊天
	 * @author waza
	 */
	public static class ChatResponseS2C extends MessageHeader
	{
		private static final long serialVersionUID = 1L;

		
		public static enum EResult
		{
			SUCCEED(),
			UNKNOW(),
			RECIVER_NOT_EXIST(),
			CHANNEL_NOT_EXIST(),
			;
		}
		
		public static ChatResponseS2C create_SUCCEED() {
			ChatResponseS2C req = new ChatResponseS2C();
			req.Result = EResult.SUCCEED;
			return req;
		}
		
		public static ChatResponseS2C create_UNKNOW() {
			ChatResponseS2C req = new ChatResponseS2C();
			req.Result = EResult.UNKNOW;
			return req;
		}
		
		public static ChatResponseS2C create_RECIVER_NOT_EXIST() {
			ChatResponseS2C req = new ChatResponseS2C();
			req.Result = EResult.RECIVER_NOT_EXIST;
			return req;
		}
		
		public static ChatResponseS2C create_CHANNEL_NOT_EXIST() {
			ChatResponseS2C req = new ChatResponseS2C();
			req.Result = EResult.CHANNEL_NOT_EXIST;
			return req;
		}

		////////////////////////////////////////////////////
		
		public EResult Result;
		
		private ChatResponseS2C() {}
		
		public String toString() {
			return "ChatResponseS2C [" + 
			Result + "]"; 
		}
	}
	
	/**
	 * 主动通知客户端的消息
	 * @author waza
	 */
	public static class ChatNotifyS2C extends MessageHeader
	{
		private static final long serialVersionUID = 1L;

		public static ChatNotifyS2C create_SingleNotify(String sender, String reciver, String message) {
			ChatNotifyS2C notify = new ChatNotifyS2C(EAction.SINGLE);
			notify.Sender = sender;
			notify.Reciver = reciver;
			notify.Message = message;
			return notify;
		}
		public static ChatNotifyS2C create_ChannelNotify(String sender, int channel, String message) {
			ChatNotifyS2C notify = new ChatNotifyS2C(EAction.CHANNEL);
			notify.Sender = sender;
			notify.Channel = channel;
			notify.Message = message;
			return notify;
		}
		public static ChatNotifyS2C create_AllNotify(String sender, String message) {
			ChatNotifyS2C notify = new ChatNotifyS2C(EAction.ALL);
			notify.Sender = sender;
			notify.Message = message;
			return notify;
		}
		
		
		////////////////////////////////////////////////////
		public static enum EAction
		{
			ALL(),
			CHANNEL(),
			SINGLE(),
			;
		}
		
		final public EAction Action;
		public String Sender;
		public String Reciver;
		public int Channel;
		public String Message;
		
		private ChatNotifyS2C(EAction action) {
			this.Action = action;
		}
		
		
		public String toString() {
			return "ChatNotifyS2C [" + 
			Action + "," +
			Sender + "," + 
			Reciver + "," +
			Channel + "," + 
			Message + "," + 
			"]"; 
		}
	}
	
	/**
	 * 对频道相关的操作
	 * @author waza
	 */
	public static class ChannelRequestC2S extends MessageHeader
	{
		private static final long serialVersionUID = 1L;

		public static ChannelRequestC2S create_Action(EAction action, int channelName) {
			ChannelRequestC2S req = new ChannelRequestC2S();
			req.Action		= action;
			req.ChannelName	= channelName;
			return req;
		}
		
		public static enum EAction
		{
			CREATE_CHANNEL(),
			JOIN_CHANNEL(),
			LEAVE_CHANNEL(),
			GET_JOINED_CHANNELS(),
			GET_CHANNEL_MEMBERS(),
			;
		}

		////////////////////////////////////////////////////
		
		public EAction Action;
		public int ChannelName;
		
		private ChannelRequestC2S() {}
		
		public String toString() {
			return "ChannelRequestC2S [" + 
			Action + "," + 
			ChannelName + "," + 
			"]"; 
		}
	}
	
	public static class ChannelResponseS2C extends MessageHeader
	{
		private static final long serialVersionUID = 1L;

		public static ChannelResponseS2C create_FAILED(ChannelRequestC2S.EAction action) {
			ChannelResponseS2C ret = new ChannelResponseS2C();
			ret.Action = action;
			ret.Result = EResult.FAILED;
			return ret;
		}
		
		public static ChannelResponseS2C create_SUCCEED(ChannelRequestC2S.EAction action) {
			ChannelResponseS2C ret = new ChannelResponseS2C();
			ret.Action = action;
			ret.Result = EResult.SUCCEED;
			return ret;
		}
		
		public static ChannelResponseS2C create_SUCCEED_CREATE_CHANNEL(int channel) {
			ChannelResponseS2C ret = new ChannelResponseS2C();
			ret.Action = ChannelRequestC2S.EAction.CREATE_CHANNEL;
			ret.Result = EResult.SUCCEED;
			ret.CreatedChannelID = channel;
			return ret;
		}
		
		public static ChannelResponseS2C create_SUCCEED_GET_CHANNEL_MEMBERS(ArrayList<String> members) {
			ChannelResponseS2C ret = new ChannelResponseS2C();
			ret.Action = ChannelRequestC2S.EAction.GET_CHANNEL_MEMBERS;
			ret.Result = EResult.SUCCEED;
			ret.Members = members;
			return ret;
		}
		
		public static ChannelResponseS2C create_SUCCEED_GET_JOINED_CHANNELS(ArrayList<Integer> channels) {
			ChannelResponseS2C ret = new ChannelResponseS2C();
			ret.Action = ChannelRequestC2S.EAction.GET_JOINED_CHANNELS;
			ret.Result = EResult.SUCCEED;
			ret.Channels = channels;
			return ret;
		}
		
		public static enum EResult
		{
			SUCCEED(),
			FAILED(),
			;
		}

		public ChannelRequestC2S.EAction Action;
		public EResult Result;
		public int CreatedChannelID;
		public ArrayList<Integer> Channels;
		public ArrayList<String> Members;
		
		private ChannelResponseS2C() {}
		
		public String toString() {
			return "ChannelResponseS2C [" + 
			Action + "," + 
			Result + "," + 
			Channels + "," + 
			Members + "]"; 
		}
	}
	
	public static class ChannelNotifyS2C extends MessageHeader
	{
		private static final long serialVersionUID = 1L;

		//
		public static ChannelNotifyS2C create_JOINED(int channel, String member) {
			ChannelNotifyS2C ret = new ChannelNotifyS2C();
			ret.Action = EAction.JOINED;
			ret.Channel = channel;
			ret.Member = member;
			return ret;
		}
		public static ChannelNotifyS2C create_LEAVED(int channel, String member) {
			ChannelNotifyS2C ret = new ChannelNotifyS2C();
			ret.Action = EAction.LEAVED;
			ret.Channel = channel;
			ret.Member = member;
			return ret;
		}
		public static ChannelNotifyS2C create_MEMBER_JOINED(int channel, String member) {
			ChannelNotifyS2C ret = new ChannelNotifyS2C();
			ret.Action = EAction.MEMBER_JOINED;
			ret.Channel = channel;
			ret.Member = member;
			return ret;
		}
		public static ChannelNotifyS2C create_MEMBER_LEAVED(int channel, String member) {
			ChannelNotifyS2C ret = new ChannelNotifyS2C();
			ret.Action = EAction.MEMBER_LEAVED;
			ret.Channel = channel;
			ret.Member = member;
			return ret;
		}
		
		public static enum EAction
		{
			JOINED(),
			LEAVED(),
			MEMBER_JOINED(),
			MEMBER_LEAVED(),
			;
			
		}
		
		public EAction Action;
		
		public int Channel;
		public String Member;
		public String Reason = "";
		
		private ChannelNotifyS2C() {}
		
		public String toString() {
			return "ChannelNotifyS2C [" + 
			Action + "," + 
			Channel + "," + 
			Member + "," + 
			Reason + "]"; 
		}
		
	}
	
	
	
}
