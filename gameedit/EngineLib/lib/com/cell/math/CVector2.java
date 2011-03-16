/**
 * 版权所有[2006][张翼飞]
 *
 * 根据2.0版本Apache许可证("许可证")授权；
 * 根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 * http://www.apache.org/licenses/LICENSE-2.0
 * 除非因适用法律需要或书面同意，
 * 根据许可证分发的软件是基于"按原样"基础提供，
 * 无任何明示的或暗示的保证或条件。
 * 详见根据许可证许可下，特定语言的管辖权限和限制。
 */
package com.cell.math;

/**
 * 向量
 * @author yifeizhang
 * @since 2006-12-18 
 * @version 1.0
 * @param <T>
 */
public class CVector2 {
	int X;
	int Y;
	
	public CVector2(int x,int y){
		X = x;
		Y = y;
	}
	public CVector2(CVector2 v){
		X = v.X;
		Y = v.Y;
	}
	
	public void add(CVector2 v){
		X += v.X ;
		Y += v.Y ;
	}
	
	public void sub(CVector2 v){
		X -= v.X ;
		Y -= v.Y ;
	}
	
	public int dotProduct(CVector2 v){
		return (X*v.X + Y*v.Y);
	}
	
	public int crossProduct(CVector2 v){
		return (X*v.Y - v.X*Y); 
	}
	
	
}
