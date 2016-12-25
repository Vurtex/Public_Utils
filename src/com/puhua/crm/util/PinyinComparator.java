package com.puhua.crm.util;

import java.util.Comparator;

import com.puhua.crm.entity.Contacts.consContentContacts;
import com.puhua.crm.entity.SortModel;

public class PinyinComparator implements Comparator<consContentContacts>
{

	public int compare(consContentContacts o1, consContentContacts o2)
	{
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) 
			return -1;
		else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) 
			return 1;
	    else 
			return o1.getSortLetters().compareTo(o2.getSortLetters());
	}

}
