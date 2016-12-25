package com.puhua.crm.util;

import java.util.Comparator;

import com.puhua.crm.entity.ClientList.consContent;

public class PinyinComparator2 implements Comparator<consContent>
{

	public int compare(consContent o1, consContent o2)
	{
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) 
			return -1;
		else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) 
			return 1;
	    else 
			return o1.getSortLetters().compareTo(o2.getSortLetters());
	}


}