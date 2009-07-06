using System;
using System.Collections.Generic;
using System.Collections;
using System.Text;
using System.IO;

namespace CellGameEdit.PM
{
    class Util
    {
        public static Encoding CurEncoding;


        public static string FormatNumberArray1D_h = "";
        public static string FormatNumberArray1D_t = ",";

        public static string FormatStringArray1D_h = "\"";
        public static string FormatStringArray1D_t = "\",";


        public static string FormatArray1D_h = "";
        public static string FormatArray1D_t = ",";
        public static string FormatArray2D_h = "{";
        public static string FormatArray2D_t = "},";

        public static bool FixedStringArray = false;
        public static string FixedStringArraySplit = "";
        public static bool FixedStringArrayByte = true;

        public static string fixControlText(string str)
        {
            string ret = str;
            ret = ret.Replace("\\n", "\n");
            ret = ret.Replace("\\t", "\t");
            //Console.WriteLine("replace ");
            return ret;
        }

        public static void setFormatStringArray1D(String format, String elementKey)
        {
            try
            {
                string[] ht = format.Split(new string[] { elementKey }, StringSplitOptions.None);
                FormatStringArray1D_h = ht[0];
                FormatStringArray1D_t = ht[1];
            }
            catch (Exception err)
            {
                FormatStringArray1D_h = "\"";
                FormatStringArray1D_t = "\",";
                //Console.WriteLine("Set Array Format Error ! Set Default. ");
            }

            FormatStringArray1D_h = fixControlText(FormatStringArray1D_h);
            FormatStringArray1D_t = fixControlText(FormatStringArray1D_t);
            //Console.WriteLine("FormatStringArray1D : " + FormatStringArray1D_h + elementKey + FormatStringArray1D_t);
        }
        public static void setFormatNumberArray1D(String format, String elementKey)
        {
            try
            {
                string[] ht = format.Split(new string[] { elementKey },StringSplitOptions.None);
                FormatNumberArray1D_h = ht[0];
                FormatNumberArray1D_t = ht[1];
            }
            catch (Exception err)
            {
                FormatNumberArray1D_h = "";
                FormatNumberArray1D_t = ",";
                //Console.WriteLine("Set Array Format Error ! Set Default. ");
            }

            FormatNumberArray1D_h = fixControlText(FormatNumberArray1D_h);
            FormatNumberArray1D_t = fixControlText(FormatNumberArray1D_t);
            //Console.WriteLine("FormatNumberArray1D : " + FormatNumberArray1D_h + elementKey + FormatNumberArray1D_t);
        }

        public static void setFormatArray1D(String format, String elementKey)
        {
            try
            {
                string[] ht = format.Split(new string[] { elementKey }, StringSplitOptions.None);
                FormatArray1D_h = ht[0];
                FormatArray1D_t = ht[1];
            }
            catch (Exception err)
            {
                FormatArray1D_h = "";
                FormatArray1D_t = ",";
                //Console.WriteLine("Set Array Format Error ! Set Default. ");
            }

            FormatArray1D_h = fixControlText(FormatArray1D_h);
            FormatArray1D_t = fixControlText(FormatArray1D_t);
            //Console.WriteLine("FormatArray1D : " + FormatArray1D_h + elementKey + FormatArray1D_t);
        }
        public static void setFormatArray2D(String format, String elementKey)
        {
            try
            {
                string[] ht = format.Split(new string[] { elementKey }, StringSplitOptions.None);
                FormatArray2D_h = ht[0];
                FormatArray2D_t = ht[1];
            }
            catch (Exception err)
            {
                FormatArray2D_h = "{";
                FormatArray2D_t = "},";
                //Console.WriteLine("Set Array Format Error ! Set Default. ");
            }

            FormatArray2D_h = fixControlText(FormatArray2D_h);
            FormatArray2D_t = fixControlText(FormatArray2D_t);
            //Console.WriteLine("FormatArray2D : " + FormatArray2D_h + elementKey + FormatArray2D_t);
        }


        public static void setFixedStringArray(string format)
        {
            try
            {
                format = format.Trim();

                if (format.Length > 0)
                {
                    if (format.StartsWith("byte"))
                    {
                        FixedStringArraySplit = format.Substring(4);
                        FixedStringArrayByte = true;
                        Console.WriteLine("FixedStringArray byte : " + format);
                    }
                    else if (format.StartsWith("char"))
                    {
                        FixedStringArraySplit = format.Substring(4);
                        FixedStringArrayByte = false;
                        Console.WriteLine("FixedStringArray char : " + format);
                    }
                    else
                    {
                        FixedStringArraySplit = format;
                        Console.WriteLine("FixedStringArray : " + format);
                    }

                    FixedStringArray = true;
                }
            }
            catch (Exception err) 
            {
                FixedStringArray = false;
            }
        }

        //---------------------------------------------------------------------------------------------------------------------------------------------


        public static int checkWildcard(String wildcard, String str)
        {
            try
            {
                char[] wc = wildcard.ToCharArray();
                char[] sc = str.ToCharArray();
                int wi = 0;
                int si = 0;
                int count = Math.Max(sc.Length,wc.Length);

                for (int i = 0; i < count ; i++)
                {
                        if (sc[si] == wc[wi])
                        {
                            wi++;
                            si++;
                            continue;
                        }
                        if (wc[wi] == '?')
                        {
                            wi++;
                            si++;
                            continue;
                        }
                        if (wc[wi] == '*')
                        {
                            if (wi + 1 < wc.Length && si + 1 < sc.Length && wc[wi + 1] == sc[si + 1])
                            {
                                wi++;
                                si++;
                            }
                            else
                            {
                                si++;
                            }
                            continue;
                        }
                        //Console.WriteLine("Wildard False : " + wildcard + " -> " + str);
                        return 1;
                    
                }
                //Console.WriteLine("Wildard True : " + wildcard + " -> " + str);
                return 0;
            }
            catch (Exception err)
            {
                //Console.WriteLine("Wildard Error : " + wildcard + " -> " + str);
                return -1;
            }
        }

        //---------------------------------------------------------------------------------------------------------------------------------------------


        //public static String toTextArray(short[] array)
        //{
        //    String ret = "";
        //    for (int i = 0; i < array.Length; i++)
        //    {
        //        ret += FormatArray1D_h + array[i].ToString() + FormatArray1D_t;
        //    }
        //    return ret;
        //}

        //public static String toTextArray(byte[] array)
        //{
        //    String ret = "";
        //    for (int i = 0; i < array.Length; i++)
        //    {
        //        ret += FormatArray1D_h + array[i].ToString() + FormatArray1D_t;
        //    }
        //    return ret;
        //}


        public static String toArray1D<T>(ref T[] array)
        {
            String ret = "";
            for (int i = 0; i < array.Length; i++)
            {
                ret += FormatArray1D_h + array[i].ToString() + FormatArray1D_t;
            }
            return ret;
        }
        public static String toArray2D<T>(ref T[][] array)
        {
            String ret = "";
            for (int i = 0; i < array.Length; i++)
            {
                ret += FormatArray2D_h + toArray1D<T>(ref array[i]) + FormatArray2D_t;
            }
            return ret;
        }

        public static String toNumberArray1D<T>(ref T[] array)
        {
            String ret = "";
            for (int i = 0; i < array.Length; i++)
            {
                string e = array[i].ToString();

                if (isNumberString(e))
                {
                    ret += FormatNumberArray1D_h + string2Number(e) + FormatNumberArray1D_t;
                }
                else
                {
                    ret += FormatNumberArray1D_h + "0" + FormatNumberArray1D_t;
                }
                
            }
            return ret;
        }
        public static String toNumberArray2D<T>(ref T[][] array)
        {
            String ret = "";
            for (int i = 0; i < array.Length; i++)
            {
                ret += FormatArray2D_h + toNumberArray1D<T>(ref array[i]) + FormatArray2D_t;
            }
            return ret;
        }

        public static String toStringArray1D<T>(ref T[] array)
        {
            String ret = "";
            for (int i = 0; i < array.Length; i++)
            {
                String e = array[i].ToString();
                if (FixedStringArray == false)
                {
                    ret += FormatStringArray1D_h + e + FormatStringArray1D_t;
                }
                else
                {
                    int size = 0;
                    if (FixedStringArrayByte)
                    {
                        size = CurEncoding.GetEncoder().GetByteCount(e.ToCharArray(), 0, e.Length, true);
                    }
                    else
                    {
                        size = e.Length;
                    }

                    ret += size + FixedStringArraySplit + FormatStringArray1D_h + e + FormatStringArray1D_t;

                }

            }
            return ret;
        }
        public static String toStringArray2D<T>(ref T[][] array)
        {
            String ret = "";
            for (int i = 0; i < array.Length; i++)
            {
                ret += FormatArray2D_h + toStringArray1D<T>(ref array[i]) + FormatArray2D_t;
            }
            return ret;
        }

        public static String toSmartArray1D<T>(ref T[] array, Type[] types)
        {
            String ret = "";
            for (int i = 0; i < array.Length; i++)
            {
                string e = array[i].ToString();

                if (types != null && types[i] != null)
                {
                    if (types[i] == typeof(String))
                    {
                        if (FixedStringArray == false)
                        {
                            ret += FormatStringArray1D_h + e + FormatStringArray1D_t;
                        }
                        else
                        {
                            int size = 0;
                            if (FixedStringArrayByte)
                            {
                                size = CurEncoding.GetEncoder().GetByteCount(e.ToCharArray(), 0, e.Length, true);
                            }
                            else 
                            {
                                size = e.Length;
                            }

                            ret += size + FixedStringArraySplit + FormatStringArray1D_h + e + FormatStringArray1D_t;
                        }
                    }
                    else if (types[i] == typeof(Decimal))
                    {
                        ret += FormatNumberArray1D_h + string2Number(e) + FormatNumberArray1D_t;
                    }
                    else
                    {
                        ret += FormatArray1D_h + e + FormatArray1D_t;
                    }
                }
                else
                {
                    if (isNumberString(e))
                    {
                        ret += FormatNumberArray1D_h + string2Number(e) + FormatNumberArray1D_t;
                    }
                    else
                    {
                        if (FixedStringArray == false)
                        {
                            ret += FormatStringArray1D_h + e + FormatStringArray1D_t;
                        }
                        else
                        {
                            int size = 0;
                            if (FixedStringArrayByte)
                            {
                                size = CurEncoding.GetEncoder().GetByteCount(e.ToCharArray(), 0, e.Length, true);
                            }
                            else
                            {
                                size = e.Length;
                            }

                            ret += size + FixedStringArraySplit + FormatStringArray1D_h + e + FormatStringArray1D_t;
                        }
                    }
                }
              
            }
            return ret;
        }
        public static String toSmartArray2D<T>(ref T[][] array, Type[][] types)
        {
            String ret = "";
            for (int i = 0; i < array.Length; i++)
            {
                ret += FormatArray2D_h + toSmartArray1D<T>(ref array[i], types==null?null:types[i]) + FormatArray2D_t;
            }
            return ret;
        }

        public static Boolean isNumberString(String str)
        {
            Decimal a = 0;
            if (Decimal.TryParse(str, System.Globalization.NumberStyles.Integer, null, out a))
            {
                return true;
            }

            return false;
        }

        public static Decimal string2Number(String str)
        {
            Decimal a = 0;
            Decimal.TryParse(str,System.Globalization.NumberStyles.Integer,null,out a);
            return a;
        }

        public static String[] toStringMultiLine(string src)
        {
            return src.Split(new char[] {'\n'});
        }

        /// 
        /// 取得一个文本文件的编码方式。如果无法在文件头部找到有效的前导符，Encoding.Default将被返回。
        /// 
        /// 文件名。
        /// 
        public static Encoding GetEncoding(string fileName)
        {
            return GetEncoding(fileName, Encoding.Default);

        }

        /// 
        /// 取得一个文本文件流的编码方式。
        /// 
        /// 文本文件流。
        /// 
        public static Encoding GetEncoding(FileStream stream)
        {

            return GetEncoding(stream, Encoding.Default);

        }

        /// 
        /// 取得一个文本文件的编码方式。
        /// 
        /// 文件名。
        /// 默认编码方式。当该方法无法从文件的头部取得有效的前导符时，将返回该编码方式。
        /// 
        public static Encoding GetEncoding(string fileName, Encoding defaultEncoding)
        {

            FileStream fs = new FileStream(fileName, FileMode.Open);

            Encoding targetEncoding = GetEncoding(fs, defaultEncoding);

            fs.Close();

            return targetEncoding;

        }

        /// 
        /// 取得一个文本文件流的编码方式。
        /// 
        /// 文本文件流。
        /// 默认编码方式。当该方法无法从文件的头部取得有效的前导符时，将返回该编码方式。
        /// 
        public static Encoding GetEncoding(FileStream stream, Encoding defaultEncoding)
        {

            Encoding targetEncoding = defaultEncoding;

            if (stream != null && stream.Length >= 2)
            {

                //保存文件流的前4个字节

                byte byte1 = 0;

                byte byte2 = 0;

                byte byte3 = 0;

                byte byte4 = 0;

                //保存当前Seek位置

                long origPos = stream.Seek(0, SeekOrigin.Begin);

                stream.Seek(0, SeekOrigin.Begin);



                int nByte = stream.ReadByte();

                byte1 = Convert.ToByte(nByte);

                byte2 = Convert.ToByte(stream.ReadByte());

                if (stream.Length >= 3)
                {

                    byte3 = Convert.ToByte(stream.ReadByte());

                }

                if (stream.Length >= 4)
                {

                    byte4 = Convert.ToByte(stream.ReadByte());

                }

                //根据文件流的前4个字节判断Encoding

                //Unicode {0xFF, 0xFE};

                //BE-Unicode {0xFE, 0xFF};

                //UTF8 = {0xEF, 0xBB, 0xBF};

                if (byte1 == 0xFE && byte2 == 0xFF)//UnicodeBe
                {

                    targetEncoding = Encoding.BigEndianUnicode;

                }

                if (byte1 == 0xFF && byte2 == 0xFE && byte3 != 0xFF)//Unicode
                {

                    targetEncoding = Encoding.Unicode;

                }

                if (byte1 == 0xEF && byte2 == 0xBB && byte3 == 0xBF)//UTF8
                {

                    targetEncoding = Encoding.UTF8;

                }

                //恢复Seek位置       

                stream.Seek(origPos, SeekOrigin.Begin);

            }

            return targetEncoding;

        }

//---------------------------------------------------------------------------------------------------------------------------------------------

        public static string fillVar(string script, string[] commands, string[] vars)
        {
            String ret = script;

            for (int i = 0; i < commands.Length; i++)
            {
                ret = ret.Replace(commands[i], vars[i]);
                Console.WriteLine("Var : " + commands[i] + " -> " + vars[i]);
            }

            return ret;
        }

        public interface CallFunctionParser
        {
            /// <summary>
            /// 
            /// </summary>
            /// <param name="args">args</param>
            /// <returns>function return value</returns>
            string parse(string function_name, string[] args);
        }

        //  eg <CALL GET CELL><COLUMN,ROW> 
        // callFunction("<CALL GET CELL><0,1>", "GET CELL", parser) 
        public static string callFunction(string src, string function_name, CallFunctionParser parser)
        {
            String function = "<CALL " + function_name + ">";

            while(true)
            {
                int i = src.IndexOf(function);

                if (i >= 0)
                {
                    String arg = getTopTrunk(src.Substring(i + function.Length), "<", ">");
                    String[] args = null;
                    if (arg != null)
                    {
                        args = arg.Substring(1, arg.Length - 2).Split(',');
                        if (args.Length == 0)
                        {
                            args = null;
                        }
                        
                    }
                    src = src.Replace(function + arg, parser.parse(function_name, args));
                }
                else break;

            }

            return src;
        }


//---------------------------------------------------------------------------------------------------------------------------------------------
        //得到指令 <command> value
        public static string getCommandScript(string script , string command)
        {
            try
            {
                if (script.Contains(command))
                {
                    int start = script.IndexOf(command) + command.Length;
                    int end = start;
                    for (int i = start; i < script.Length;i++ )
                    {
                        if (script[i] == '\n') 
                        { 
                            end = i; 
                            break; 
                        }
                    }
                    string ret = script.Substring(start, end - start + 1);

                    return ret.Trim();
                }
            }
            catch (Exception err) {}
            return "";
        }

        //得到指令 <command> value
        public static string[] getCommandScripts(string script, string command)
        {
            string[] ret;

            try
            {
                if (script.Contains(command))
                {
                    int start = script.IndexOf(command) + command.Length;
                    int end = start;

                    for (int i = start; i < script.Length; i++)
                    {
                        if (script[i] == '\n')
                        {
                            end = i;
                            break;
                        }
                    }
                    string cmds = script.Substring(start, end - start + 1);
                    cmds = cmds.Trim();
                    ret = cmds.Split(new char[] { ' ','\t','\n' },StringSplitOptions.RemoveEmptyEntries);

                    return ret;
                }
            }
            catch (Exception err) { }
            return null;
        }

        //删除包含 #<start> #<end> 内的内容
        public static string removeTrunkScript(string script, string start, string end)
        {
            try
            {
                int first = script.IndexOf(start);
                int last = script.IndexOf(end) + end.Length;
                string ret = script.Remove(first, last - first);
                
                return ret;
            }
            catch (Exception err)
            {
                return script.Insert(0, "/* remove trunk ERROR " + start + "->" + end + ": " + err.StackTrace + "  at  " +err.Message + " */ ");
            }
        }

        //得到包含 #<start> #<end> 内的内容
        public static string getFullTrunkScript(string script, string start, string end)
        {
            try
            {
                int first = script.IndexOf(start) ;
                int last = script.IndexOf(end) + end.Length ;
                string ret = script.Substring(first, last - first);
                return ret;
            }
            catch (Exception err)
            {
                return script.Insert(0, "/* get full trunk ERROR " + start + "->" + end + ": " + err.StackTrace + "  at  " +err.Message + " */ ");
            }
        }

        //得到包含 #<start> #<end> 内的内容, 把 dst<V>[] -> src<K>[] ,返回的不包含 #<start> 和 #<end>
        public static string replaceKeywordsScript(string script, string start, string end, string[] src,string[] dst)
        {
            try
            {
                int first = script.IndexOf(start) + start.Length;
                int last = script.IndexOf(end);
                string ret = script.Substring(first, last - first);
                
                if (src != null && dst != null)
                {
                    for (int i = 0; i < src.Length ; i++)
                    {
                        ret = ret.Replace(src[i], dst[i]);
                    }
                }
                
                return ret;
            }
            catch (Exception err) 
            {
                return script.Insert(0, "/* replace keywords ERROR " + start + "->" + end + ": " + err.StackTrace + "  at  " +err.Message + " */ ");
            }
        }

        //用dst<V>[count]填充 count个 #<start> #<end> 内的内容,返回的不包含 #<start> 和 #<end>
        public static string replaceSubTrunksScript(string script, string start, string end, string[] dst)
        {
            try
            {
                int first = script.IndexOf(start);
                int last = script.IndexOf(end) + end.Length;
                string ret = script.Substring(0, script.Length);
                if (first < 0 || last-end.Length < 0) return null;

                if (dst != null)
                {
                    for (int i = dst.Length - 1; i>=0; i--)
                    {
                        ret = ret.Insert(first, dst[i]);
                    }
                }

                ret = removeTrunkScript(ret, start, end);

                return ret;
            }
            catch (Exception err)
            {
                Console.WriteLine( "/* replace sub trunks ERROR " + start + "->" + end + " : "+err.StackTrace + "  at  " +err.Message+" */ ");
                return null; 
            }
        }

        //得到包含 #<start> #<end> 内的内容 如果不包含，返回 null
        public static string getTopTrunk(string script, string start, string end)
        {
            try
            {
                int first = script.IndexOf(start);
                int last = script.IndexOf(end) + end.Length;
                if (first < 0 || last - end.Length < 0) return null;
                string ret = script.Substring(first, last - first);

                return ret;
            }
            catch (Exception err)
            {
                return null;
            }
        }

        //测试name是否是Ignore
        public static bool testIgnore(String key,String src, String name, ref string outIgnore)
        {
            String[] ignores = Util.getCommandScripts(src, key);

            if (ignores != null)
            {
                try
                {
                    for (int ig = 0; ig < ignores.Length; ig++)
                    {
                        if(ignores[ig] == name || Util.checkWildcard(ignores[ig], name) == 0)
                        {
                            outIgnore = ignores[ig];
                            Console.WriteLine("Ignore : " + key + " : " + outIgnore + " --> " + name);
                            return true;
                        }
                    }
                    return false;
                }
                catch (Exception err)
                {
                    Console.Write("Error : " + err.Message);
                }
            }

            return false;
        }

        //测试name是否是Keep
        public static bool testKeep(String key, String src, String name, ref string outKeep)
        {
            String[] keeps = Util.getCommandScripts(src, key);

            if (keeps != null)
            {
                try
                {
                    for (int ig = 0; ig < keeps.Length; ig++)
                    {
                        if (keeps[ig] == name || Util.checkWildcard(keeps[ig], name) == 0)
                        {
                            outKeep = keeps[ig];
                            Console.WriteLine("Keep : " + key + " : " + outKeep + " --> " + name);
                            return true;
                        }
                    }
                    return false;
                }
                catch (Exception err)
                {
                    Console.Write("Error : " + err.Message);
                }
            }

            return true;
        }

        //删除含有指令的行
        public static string removeCommandLine(string src, string command)
        {
            int index = src.IndexOf(command);

            while (index >= 0)
            {
                int end = src.IndexOf('\n', index + command.Length);

                src = src.Remove(index, end >= 0 ? src.Length - end - 1 : src.Length - index);

                index = src.IndexOf(command);
            }

            return src;
        }


//---------------------------------------------------------------------------------------------------------------------------------------------
// function script 
       
        //解析所有函数指令
        public static string replaceFuncScript(String script)
        {
            String ret = script;
            while (containsFunction(ret) >= 0)
            {
                ret = fillFunction(ret);
            }
            return ret;
        }


        //得到函数参数块  ..., ..., ... 不包含 "<"">"
        public static string getFunctionArgTrunk(String script,String funcName)
        {
            String ret = null;
            try
            {
                int i = script.IndexOf(funcName);
                if (i >= 0)
                {
                    i += funcName.Length;
                    i = script.IndexOf("<", i);
                    if (i >= 0)
                    {
                        char[] chars = script.ToCharArray(i, script.Length - i);

                        int start = 0;
                        int end = 0;

                        int lc = 0;
                        int rc = 0;

                        for (; end < chars.Length; end++)
                        {
                            if (chars[end] == '<') lc++;
                            if (chars[end] == '>') rc++;
                            if (lc == rc)
                            {
                               
                                ret = new string(chars, start + 1, end - start - 1);
                                break;
                            }
                        }
                    }
                }
            }
            catch (Exception err)
            {
                ret = null;
            }
            //if (ret != null)
            //{
            //    Console.WriteLine(" getFunctionArgTrunk : " + ret);
            //}
           
            return ret;
        }

        //得到函数快 FuncName< ...... >
        public static string getFunctionTrunk(String script,String funcName, ref String outFuncArg)
        {
            String ret = null;
            try
            {
                int i = script.IndexOf(funcName);

                if (i >= 0)
                {
                    string arg = getFunctionArgTrunk(script.Substring(i),funcName);
                    if (arg != null)
                    {
                        outFuncArg = arg;
                        ret = funcName + "<" + arg + ">";
                    }
                }
            }
            catch (Exception err)
            {
                ret = null;
            }

            //if (ret != null)
            //{
            //    Console.WriteLine(" getFunctionTrunk : " + ret);
            //}
            return ret;

        }


//---------------------------------------------------------------------------------------------------------------------------------------------
// sub function script 
 
        // 所有种类的函数处理
        #region functions

    

        private static String FUNC_SUB_STRING = "<CALL SUB STRING>";
        private static Boolean fillFunction_SubString(String funcTrunk,String arg,ref String output)
        {
            string ret = "";
            try
            {
                string[] args = arg.Trim().Split(new char[] { ',' });
                if(args.Length==3 && funcTrunk.Contains(FUNC_SUB_STRING))
                {
                    ret = args[0].Substring(Int32.Parse(args[1]), Int32.Parse(args[2]));
                    output = ret;
                    return true;
                }
                if (args.Length == 2 && funcTrunk.Contains(FUNC_SUB_STRING))
                {
                    ret = args[0].Substring(Int32.Parse(args[1]));
                    output = ret;
                    return true;
                }
            }
            catch (Exception err)
            {
                ret =  err.Message ;
            }
            output = "/* Error Call : fillFunction_SubString : " + ret + " */";
            return false;
        }

        private static String FUNC_PARSE_TO_INT = "<CALL PARSE TO INT>";
        private static Boolean fillFunction_ParseToInt(String funcTrunk, String arg, ref String output)
        {
            string ret = "";
            try
            {
                if (funcTrunk.Contains(FUNC_PARSE_TO_INT))
                {
                    String v = arg.Trim();
                    if (v != "")
                    {
                        Decimal dec = Decimal.Parse(v);
                        Int32 value = (Int32)dec;
                        ret = value.ToString();
                        output = ret;
                    }
                    else
                    {
                        output = "0";
                    }
                    return true;

                }
            }
            catch (Exception err)
            {
                ret = err.Message;
            }
            output = "/* Error Call : fillFunction_ParseToInt : " + ret + " */";
            return false;
        }

        private static String FUNC_PARSE_TO_DECIMAL = "<CALL PARSE TO DECIMAL>";
        private static Boolean fillFunction_ParseToDecimal(String funcTrunk, String arg, ref String output)
        {
            string ret = "";
            try
            {
                if (funcTrunk.Contains(FUNC_PARSE_TO_DECIMAL))
                {
                    String v = arg.Trim();
                    if (v != "")
                    {
                        ret = Decimal.Parse(v).ToString();
                        output = ret;
                    }
                    else
                    {
                        output = "0";
                    }
                    return true;
                }
            }
            catch (Exception err)
            {
                ret = err.Message;
            }
            output = "/* Error Call : fillFunction_ParseToDecimal : " + ret + " */";
            return false;
        }

        private static String FUNC_ADD = "<CALL ADD>";
        private static Boolean fillFunction_Add(String funcTrunk, String arg, ref String output)
        {
            string ret = "";
            try
            {
                string[] args = arg.Trim().Split(new char[] { ',' });
                if (args.Length == 2 && funcTrunk.Contains(FUNC_ADD))
                {
                    Decimal a1 = Decimal.Parse(args[0]);
                    Decimal a2 = Decimal.Parse(args[1]);
                    ret = (a1 + a2).ToString();
                    output = ret;
                    return true;
                }
            }
            catch (Exception err)
            {
                ret = err.Message;
            }
            output = "/* Error Call : fillFunction_Add : " + ret + " */";
            return false;
        }

        private static String FUNC_SUB = "<CALL SUB>";
        private static Boolean fillFunction_Sub(String funcTrunk, String arg, ref String output)
        {
            string ret = "";
            try
            {
                string[] args = arg.Trim().Split(new char[] { ',' });
                if (args.Length == 2 && funcTrunk.Contains(FUNC_SUB))
                {
                    Decimal a1 = Decimal.Parse(args[0]);
                    Decimal a2 = Decimal.Parse(args[1]);
                    ret = (a1 - a2).ToString();
                    output = ret;
                    return true;
                }
            }
            catch (Exception err)
            {
                ret = err.Message;
            }
            output = "/* Error Call : fillFunction_Sub : " + ret + " */";
            return false;
        }

        private static String FUNC_MUL = "<CALL MUL>";
        private static Boolean fillFunction_Mul(String funcTrunk, String arg, ref String output)
        {
            string ret = "";
            try
            {
                string[] args = arg.Trim().Split(new char[] { ',' });
                if (args.Length == 2 && funcTrunk.Contains(FUNC_MUL))
                {
                    Decimal a1 = Decimal.Parse(args[0]);
                    Decimal a2 = Decimal.Parse(args[1]);
                    ret = (a1 * a2).ToString();
                    output = ret;
                    return true;
                }
            }
            catch (Exception err)
            {
                ret = err.Message;
            }
            output = "/* Error Call : fillFunction_Mul : " + ret + " */";
            return false;
        }

        private static String FUNC_DIV = "<CALL DIV>";
        private static Boolean fillFunction_Div(String funcTrunk, String arg, ref String output)
        {
            string ret = "";
            try
            {
                string[] args = arg.Trim().Split(new char[] { ',' });
                if (args.Length == 2 && funcTrunk.Contains(FUNC_DIV))
                {
                    Decimal a1 = Decimal.Parse(args[0]);
                    Decimal a2 = Decimal.Parse(args[1]);
                    ret = (a1 / a2).ToString();
                    output = ret;
                    return true;
                }
            }
            catch (Exception err)
            {
                ret = err.Message;
            }
            output = "/* Error Call : fillFunction_Div : " + ret + " */";
            return false;
        }

        #endregion

        private static String[] FUNC_COMMANDS = new String[] { 
            FUNC_SUB_STRING,
            FUNC_PARSE_TO_INT,
            FUNC_PARSE_TO_DECIMAL,
            FUNC_ADD,
            FUNC_SUB,
            FUNC_MUL,
            FUNC_DIV,
        };
//---------------------------------------------------------------------------------------------------------------------------------------------

        //判断字符块内第一个函数的参数块
        public static string getFunctionTrunk(string funcTrunk, ref String outFuncArg)
        {
            string ret = null;

            for (int i = 0; i < FUNC_COMMANDS.Length;i++ )
            {
                ret = getFunctionTrunk(funcTrunk, FUNC_COMMANDS[i], ref outFuncArg);
                if (ret != null) return ret;
            }

            //ret = getFunctionTrunk(funcTrunk, FUNC_SUB_STRING, ref outFuncArg);
            //if (ret != null) return ret;

            //ret = getFunctionTrunk(funcTrunk, FUNC_PARSE_TO_INT, ref outFuncArg);
            //if (ret != null) return ret;

            //ret = getFunctionTrunk(funcTrunk, FUNC_PARSE_TO_DECIMAL, ref outFuncArg);
            //if (ret != null) return ret;

            return null;
        }

        //判断字符块内是否有函数
        public static int containsFunction(String funcTrunk)
        {
            int ret = -1;

            for (int i = 0; i < FUNC_COMMANDS.Length; i++)
            {
                ret = funcTrunk.IndexOf(FUNC_COMMANDS[i]);
                if (ret >= 0) return ret;
            }

            //ret = funcTrunk.IndexOf(FUNC_SUB_STRING);
            //if (ret >= 0) return ret;

            //ret = funcTrunk.IndexOf(FUNC_PARSE_TO_INT);
            //if (ret >= 0) return ret;

            //ret = funcTrunk.IndexOf(FUNC_PARSE_TO_DECIMAL);
            //if (ret >= 0) return ret;

            return -1;
        }

        //填充字符块内的第一个函数=>返回值
        public static string fillFunction(String funcTrunk)
        {
            string ret = funcTrunk;
            string arg = null;
            string func = getFunctionTrunk(funcTrunk, ref arg);

            if (func!=null && arg!=null)
            {
                String funcRet = "";

                while (containsFunction(arg) >= 0)//如果参数内包含其函数则继续调用
                {
                    arg = fillFunction(arg);
                }

                if (fillFunction_SubString(func, arg, ref funcRet))//sub string
                {
                    ret = funcTrunk.Replace(func, funcRet);
                }
                else if (fillFunction_ParseToInt(func, arg, ref funcRet))//to int
                {
                    ret = funcTrunk.Replace(func, funcRet);
                }
                else if (fillFunction_ParseToDecimal(func, arg, ref funcRet))//to decimal
                {
                    ret = funcTrunk.Replace(func, funcRet);
                }
                else if (fillFunction_Add(func, arg, ref funcRet))//add
                {
                    ret = funcTrunk.Replace(func, funcRet);
                }
                else if (fillFunction_Sub(func, arg, ref funcRet))//sub
                {
                    ret = funcTrunk.Replace(func, funcRet);
                }
                else if (fillFunction_Mul(func, arg, ref funcRet))//mul
                {
                    ret = funcTrunk.Replace(func, funcRet);
                }
                else if (fillFunction_Div(func, arg, ref funcRet))//div
                {
                    ret = funcTrunk.Replace(func, funcRet);
                }


                return ret; 
                    
            }

            return funcTrunk;

        }


        //得到c1 c2 包含的字符串， 56(32)44 = 32
        public static string getIncludeString(string src, string c1, string c2)
        {
            try
            {
                int p1 = src.IndexOf(c1) + c1.Length;
                int p2 = src.IndexOf(c2);
                return src.Substring(p1, p2 - p1);
            }
            catch (Exception err)
            {
                return "";
            }
        }



        //---------------------------------------------------------------------------------------------------------------------------------------------

        /// math

        public static int getDirect(int s)
        {
            return s == 0 ? 0 : s > 0 ? 1 : -1;
        }


    }

















}

