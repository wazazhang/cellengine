package com.cell.sql;

import java.util.List;


/**
 * <b>用来描述一个列表。</b><br>
 * 比如一个表中包含一个列表。<br>
<pre>
class MyTable
{
    {@link @SQLArrayField(type=SQLType.INTEGER, defaultValue="0", comment="array", limit=8)}
    MyArray array = new MyArray();
}
</pre>
 * 此类转换成SQL表结构为<br>
<pre>
CREATE TABLE MyTable(
	array__0 integer DEFAULT '0',
	array__1 integer DEFAULT '0',
	array__2 integer DEFAULT '0',
	array__3 integer DEFAULT '0',
	array__4 integer DEFAULT '0',
	array__5 integer DEFAULT '0',
	array__6 integer DEFAULT '0',
	array__7 integer DEFAULT '0',
);
</pre>
 * 
 * @author WAZA
 */
public interface SQLFieldArray<T>
{
	public List<T> getArray();
}
