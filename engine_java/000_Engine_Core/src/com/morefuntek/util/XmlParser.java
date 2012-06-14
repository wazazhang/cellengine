package com.morefuntek.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 文件：XMLParser.java<br>
 * <br>
 * 授权：DingCHU<br>
 * <br>
 * 时间：2012-6-12<br>
 * <br>
 * 版本：1.0<br>
 * <br>
 * 归属： 上海美峰数码科技有限公司<br>
 * <br>
 * 描述：XML文本解析器
 */

public final class XmlParser
{
    /**
     * 获取属性列表
     * 
     * @param _xmlFileName
     * @param _propertyNodeName
     * @return
     */
    public static List<Properties> _2PropertiesList (String _xmlFileName,
            String _propertyNodeName)
    {
        List<Node> dataNodeList = parse(_xmlFileName, _propertyNodeName);

        if (null != dataNodeList)
        {
            ArrayList<Properties> propertiesList = new ArrayList<Properties>();
            NodeList nodeList;
            Node childNode;
            Properties p;

            for (Node node : dataNodeList)
            {
                nodeList = node.getChildNodes();
                p = new Properties();

                for (int j = 0; j < nodeList.getLength(); j++)
                {
                    childNode = nodeList.item(j);

                    if (Node.ELEMENT_NODE == childNode.getNodeType())
                        p.put(childNode.getNodeName().trim(), childNode
                                .getTextContent());
                }

                propertiesList.add(p);
            }

            if (propertiesList.size() > 0) return propertiesList;
        }

        return null;
    }

    /**
     * 获取对象列表
     * 
     * @param <T>
     * @param _c
     * @param _xmlFile
     * @param _dataNodeName
     * @return
     * @throws Exception
     */
    public static <T extends Object> List<T> _2ObjectList (Class<T> _c,
            String _xmlFileName, String _dataNodeName) throws Exception
    {
        List<Node> dataNodeList = parse(_xmlFileName, _dataNodeName);

        if (null != dataNodeList)
        {
            ArrayList<T> objectList = new ArrayList<T>();
            NodeList nodeList;
            Node childNode;
            T o;
            Field f;

            for (Node node : dataNodeList)
            {
                nodeList = node.getChildNodes();
                o = _c.newInstance();

                for (int j = 0; j < nodeList.getLength(); j++)
                {
                    childNode = nodeList.item(j);

                    if (Node.ELEMENT_NODE == childNode.getNodeType())
                    {
                        f = _c.getField(childNode.getNodeName().trim());
                        dealVaule(o, f, childNode.getTextContent());
                    }
                }

                objectList.add(o);
            }

            if (objectList.size() > 0) return objectList;
        }

        return null;
    }

    /**
     * 获取XML文本中指定节点名中的子节点列表
     * 
     * @param _xmlFileName
     * @param _dataNodeName
     * @return
     */
    private static List<Node> parse (String _xmlFileName, String _dataNodeName)
    {
        try
        {
            File xmlFile = new File(_xmlFileName);

            if (xmlFile.exists())
            {
                DocumentBuilderFactory domfac = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder dombuilder = domfac.newDocumentBuilder();
                Document doc = dombuilder.parse(xmlFile);
                NodeList elements = doc.getElementsByTagName(_dataNodeName);

                if (elements != null)
                {
                    List<Node> nodeList = new ArrayList<Node>();

                    for (int i = 0; i < elements.getLength(); i++)
                        nodeList.add(elements.item(i));

                    return nodeList;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param o 要转换的对象
     * @param f 属性
     * @param content 属性值
     */
    private static void dealVaule (Object o, Field f, String content)
    {
        Class<?> c = f.getType();

        try
        {
            if (int.class.getName().equals(c.getName()))
            {
                f.setInt(o, Integer.parseInt(content));
            }
            else if (short.class.getName().equals(c.getName()))
            {
                f.setShort(o, Short.parseShort(content));
            }
            if (byte.class.getName().equals(c.getName()))
            {
                f.setByte(o, Byte.parseByte(content));
            }
            else if (String.class.getName().equals(c.getName()))
            {
                f.set(o, content);
            }
            else if (boolean.class.getName().equals(c.getName()))
            {
                f.setBoolean(o, Boolean.parseBoolean(content));
            }
            else if (float.class.getName().equals(c.getName()))
            {
                if (!content.equals(""))
                    f.setFloat(o, Float.parseFloat(content));
            }
            else if (double.class.getName().equals(c.getName()))
            {
                if (!content.equals(""))
                    f.setDouble(o, Double.parseDouble(content));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
