package com.ctgu.audit;

/**
 * @ClassName: OperationType
 * @Description: 数据库操作类型：新增，修改，删除
 * @author lh2
 * @date 2020年6月12日 下午5:46:05
 */
public enum OperationType
{
	ADD(0, "新增"),  //
	UPDATE(1, "修改"),  //
	DELETE(2, "删除"); //

	private int index;
	private String name;

	private OperationType(int index, String name)
	{
		this.index = index;
		this.name = name;
	}

	public static String getName(int index)
	{
		for (OperationType op : OperationType.values())
		{
			if (index == op.getIndex())
			{
				return op.name;
			}
		}
		return null;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
