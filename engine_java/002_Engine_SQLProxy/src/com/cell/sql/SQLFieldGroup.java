package com.cell.sql;

import com.cell.reflect.FieldGroup;

/**
 * 用来描述多个字段的组合，该字段包含的字段将存储为表的字段，
 * 组合字段可以包含组合字段，这是一个递归的过程。
 * 整个体系可以被描述成一个树形结构。
 * @author WAZA
 */
public interface SQLFieldGroup extends FieldGroup
{

}
