JOURNALDEV

JAVA, JAVA EE, ANDROID, PYTHON, WEB DEVELOPMENT TUTORIALS


JAVA TUTORIAL
#INDEX POSTS
Core Java Tutorial
Java Design Patterns
Servlet JSP Tutorial
Struts 2 Tutorial
Spring Tutorial
JSF Tutorial
Primefaces Tutorial
JDBC Tutorial
Hibernate Tutorial
MongoDB Tutorial
#INTERVIEW QUESTIONS
Java Interview Questions
Core Java Interview Questions
JDBC Interview Questions
Servlet Interview Questions
JSP Interview Questions
Struts2 Interview Questions
Spring Interview Questions
Hibernate Interview Questions
JSF Interview Questions
RESOURCES
YOU ARE HERE: HOME » JAVA » HOW TO CREATE IMMUTABLE CLASS IN JAVA?
How to Create Immutable Class in java?
PANKAJ 72 COMMENTS


Today we will learn how to create an immutable class in Java. Immutable objects are instances whose state doesn’t change after it has been initialized. For example, String is an immutable class and once instantiated its value never changes.

Read: Why String in immutable in Java

Immutable Class in Java
immutable class in java, how to create immutable class in java, how to  make a class immutable

An immutable class is good for caching purpose because you don’t need to worry about the value changes. Other benefit of immutable class is that it is inherently thread-safe, so you don’t need to worry about thread safety in case of multi-threaded environment.

Read: Java Thread Tutorial and Java Multi-Threading Interview Questions.

Here I am providing a way to create an immutable class in Java via an example for better understanding.


To create an immutable class in java, you have to do following steps.

Declare the class as final so it can’t be extended.
Make all fields private so that direct access is not allowed.
Don’t provide setter methods for variables
Make all mutable fields final so that it’s value can be assigned only once.
Initialize all the fields via a constructor performing deep copy.
Perform cloning of objects in the getter methods to return a copy rather than returning the actual object reference.
To understand points 4 and 5, let’s run the sample Final class that works well and values don’t get altered after instantiation.

FinalClassExample.java


Copy
package com.journaldev.java;

import java.util.HashMap;
import java.util.Iterator;

public final class FinalClassExample {

	private final int id;
	
	private final String name;
	
	private final HashMap<String,String> testMap;
	
	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	/**
	 * Accessor function for mutable objects
	 */
	public HashMap<String, String> getTestMap() {
		//return testMap;
		return (HashMap<String, String>) testMap.clone();
	}

	/**
	 * Constructor performing Deep Copy
	 * @param i
	 * @param n
	 * @param hm
	 */
	
	public FinalClassExample(int i, String n, HashMap<String,String> hm){
		System.out.println("Performing Deep Copy for Object initialization");
		this.id=i;
		this.name=n;
		HashMap<String,String> tempMap=new HashMap<String,String>();
		String key;
		Iterator<String> it = hm.keySet().iterator();
		while(it.hasNext()){
			key=it.next();
			tempMap.put(key, hm.get(key));
		}
		this.testMap=tempMap;
	}
	
	
	/**
	 * Constructor performing Shallow Copy
	 * @param i
	 * @param n
	 * @param hm
	 */
	/**
	public FinalClassExample(int i, String n, HashMap<String,String> hm){
		System.out.println("Performing Shallow Copy for Object initialization");
		this.id=i;
		this.name=n;
		this.testMap=hm;
	}
	*/
	
	/**
	 * To test the consequences of Shallow Copy and how to avoid it with Deep Copy for creating immutable classes
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, String> h1 = new HashMap<String,String>();
		h1.put("1", "first");
		h1.put("2", "second");
		
		String s = "original";
		
		int i=10;
		
		FinalClassExample ce = new FinalClassExample(i,s,h1);
		
		//Lets see whether its copy by field or reference
		System.out.println(s==ce.getName());
		System.out.println(h1 == ce.getTestMap());
		//print the ce values
		System.out.println("ce id:"+ce.getId());
		System.out.println("ce name:"+ce.getName());
		System.out.println("ce testMap:"+ce.getTestMap());
		//change the local variable values
		i=20;
		s="modified";
		h1.put("3", "third");
		//print the values again
		System.out.println("ce id after local variable change:"+ce.getId());
		System.out.println("ce name after local variable change:"+ce.getName());
		System.out.println("ce testMap after local variable change:"+ce.getTestMap());
		
		HashMap<String, String> hmTest = ce.getTestMap();
		hmTest.put("4", "new");
		
		System.out.println("ce testMap after changing variable from accessor methods:"+ce.getTestMap());

	}

}
Output of the above immutable class in java example program is:


Performing Deep Copy for Object initialization
true
false
ce id:10
ce name:original
ce testMap:{2=second, 1=first}
ce id after local variable change:10
ce name after local variable change:original
ce testMap after local variable change:{2=second, 1=first}
ce testMap after changing variable from accessor methods:{2=second, 1=first}
Now let’s comment the constructor providing deep copy and uncomment the constructor providing a shallow copy. Also uncomment the return statement in getTestMap() method that returns the actual object reference and then execute the program once again.


Performing Shallow Copy for Object initialization
true
true
ce id:10
ce name:original
ce testMap:{2=second, 1=first}
ce id after local variable change:10
ce name after local variable change:original
ce testMap after local variable change:{3=third, 2=second, 1=first}
ce testMap after changing variable from accessor methods:{3=third, 2=second, 1=first, 4=new}
As you can see from the output, HashMap values got changed because of shallow copy in the constructor and providing a direct reference to the original object in the getter function.

That’s all for how to create an immutable class in java. If I have missed something here, feel free to comment.

Further Reading: If the immutable class has a lot of attributes and some of them are optional, we can use builder pattern to create immutable classes.

You can check out more Java examples from our GitHub Repository.

 
WHATSAPP	REDDIT	TWITTER	FACEBOOK	LINKEDIN	EMAIL
 PREVIOUS 
ConcurrentHashMap in JavaNEXT 
StringBuffer vs StringBuilder

About Pankaj
If you have come this far, it means that you liked what you are reading. Why not reach little more and connect with me directly on Google Plus, Facebook or Twitter. I would love to hear your thoughts and opinions on my articles directly.

Recently I started creating video tutorials too, so do check out my videos on Youtube.

FILED UNDER: JAVA


Comments
Ravi Beli says

MARCH 11, 2019 AT 6:22 PM

Even within this constructor if you assign cloned version of input “hm” to testMap, it should still work, no need of deep copy.
public FinalClassExample(int i, String n, HashMap hm){
System.out.println(“Performing Shallow Copy for Object initialization”);
this.id=i;
this.name=n;
this.testMap=hm.clone();
}

Reply
Satish says

MARCH 18, 2019 AT 2:15 PM

Perfect answer with a small change. You need to type cast when calling the clone method as it return object.

Reply
Pandidurai says

JANUARY 31, 2019 AT 4:26 AM

Hi,

First i want to thank you for sharing your knowledge with us.

here i have one question with this

if i am getting HashMap reference through getter method then i can avoid changing Immutable class value by sending deep copy. But what if user accessed it directly? i mean

Map local = ce.testMap;

local.put(“local” , “local”);

in this case ce.testMap will have the above added object as well.

How can we avoid this?

thanks!

Reply
Rishabh says

FEBRUARY 11, 2019 AT 6:10 AM

while returning map return new Object.

So the getter method of testMap will look like getTestMap{
return new HashMap(testMap);
}

Reply
vkas says

FEBRUARY 21, 2019 AT 3:05 AM

Hi Pandidurai , testMap access is private as rule, so you can not access outside class directly, without getter you can not access directly

Reply
online java training says

JANUARY 3, 2019 AT 1:15 AM

It was worth a read about immutable class in Java! Really learned a lot form this article. I can imagine the effort you put into this & especially appreciate you sharing it. keep up the good work.

Reply
Deepak says

DECEMBER 18, 2018 AT 9:12 AM

Hi,
please post your Java code of connection pooling in java without using Spring and hibernate.

Reply
Parvise says

OCTOBER 11, 2018 AT 11:02 PM

Not required map iteration in constructor and return statement clone which you used, may be any other reason please provide that.

class Student {
private final int sid;
private final String sname;
private final Map map;

public Student(int sid, String sname, Map map) {
super();
this.sid = sid;
this.sname = sname;
this.map = new HashMap(map);
//this.list=list;
}

public int getSid() {
return sid;
}

public String getSname() {
return sname;
}

public Map getList() {
return map;
}

@Override
public String toString() {
return “Student [sid=” + sid + “, sname=” + sname + “]”;
}

}

Reply
Pankaj says

OCTOBER 11, 2018 AT 11:09 PM

Yes, we can use the HashMap constructor too. However, the idea here was to showcase how to perform the deep copy. Not every object constructor provides this feature, in that case, we need to perform field by field copy so that the original object and the cloned object have different references.

Reply
u.mang says

SEPTEMBER 24, 2018 AT 7:18 PM

This is how we create an immutable class. But can you please describe how the immutable object is created? or is that happens in java or not??

Reply
Pankaj says

SEPTEMBER 24, 2018 AT 8:56 PM

A class is a design and Object is the actual implementation. When we say immutable class, it means immutable objects too.

Reply
Vijay Kumar says

SEPTEMBER 22, 2018 AT 3:29 AM

Article is very nice and easy to understand. I have gone through the comments and got deeper understanding of this concept. Requesting everyone else to go through the discussions done in comments then you will get more understanding about this topic. Thanks to Pankaj.

Reply
Omkar J says

AUGUST 28, 2018 AT 2:24 AM

Great Article…… very helpful in cracking interviews …. Thank you so much !!

Reply
Jitendra Singh says

JUNE 24, 2018 AT 10:00 AM

Hi Pankaj,

Thanks for writing such an informative article.

I would like to know what if my all member variables are immutable like all Strings or Wrappers?, Do I still need to follow above steps?

Thanks,

Reply
Pankaj says

JUNE 24, 2018 AT 10:42 AM

Yes, most of them. You can get rid of deep-copy logic if the variables are immutable.

Reply
Prahlad Kumar says

MAY 19, 2018 AT 4:19 AM

Hi Pankaj,

What will happen if we do not declare class as final, since member variables are privare so can not be extendable.

I am here trying to understand why we need final for class and its member variable.
Is there any way state of a class can be modified if we don’t declare class and its variables final?

Thanks,
Prahlad.

Reply
Gunjal Shrivastava says

AUGUST 16, 2018 AT 4:44 AM

You need the class as final so, that it cannot be extended further and it’s implememtation should not be changed by overriding the methods and by creating new variables.

Making variables as final will make sure two things,
1. Compiler will flag error if any reassignment will take place

2. It will give readability to the code to other people who are working on the same class that these variables are not supposed to be changed. Basically it is because of IMHO

Reply
Hagos haile says

APRIL 27, 2018 AT 8:01 AM

correct output is:

Performing Shallow Copy for Object initialization
true
false
ce id:10
ce name:original
ce testMap:{1=first, 2=second}
ce id after local variable change:10
ce name after local variable change:original
ce testMap after local variable change:{1=first, 2=second, 3=third}
ce testMap after changing variable from accessor methods:{1=first, 2=second, 3=third}

Reply
TEJENDRA PRATAP SINGH says

JANUARY 29, 2018 AT 11:11 AM

Your getter method for HashMap can be in that way
public HashMap getTestMap() {
//return testMap;
//HashMap tempMap=new HashMap();

return new HashMap(testMap);
}

Reply
Vaibhav Jetly says

SEPTEMBER 23, 2017 AT 2:03 AM

What if we remove the final access modifier from class as we are independently handling all the fields or methods of this class. And if some one extend this class then they doesn’t impact this class instance. Please suggest.

Reply
Mike New says

NOVEMBER 23, 2018 AT 6:22 AM

But then it is circumventing the intent of the class.

If you want additional behavior from a different class, create a different class and have the immutable one as an instance member of that class.

Reply
Sameera says

SEPTEMBER 12, 2017 AT 9:32 PM

I also wrote an article with a complete different view and you may have a look,

http://www.codedjava.com/2017/09/immutable-objects-in-java_50.html

Thanks,

Reply
kuldeep patil says

JULY 6, 2017 AT 12:09 AM

I am not clear on Point 5 initialization

swallow or Deep are comparison not initialization.
swallow comparison is done by == and Deep comparison by equal / equalignorecase

Did you mean initialization should be done at constructor with safer way without exposing identity of the fields?

Reply
Rushabh says

JUNE 24, 2017 AT 10:47 PM

Correct output is::
Performing Shallow Copy for Object initialization
true
false
ce id:10
ce name:original
ce testMap:{2=second, 1=first}
ce id after local variable change:10
ce name after local variable change:original
ce testMap after local variable change:{3=third, 2=second, 1=first}
ce testMap after changing variable from accessor methods:{3=third, 2=second, 1=f
irst}

Reply
Tanmai says

MAY 13, 2017 AT 8:05 PM

Which Java version ? or forgot the Final variables initialisation?

Reply
Manoj Kumar Vohra says

MAY 22, 2017 AT 8:00 AM

Final variables can be left uninitialized in declaration if initialization is provided by constructor.

Reply
Anbu says

APRIL 13, 2017 AT 12:28 AM

Hi setter method for map provides shallow copy only though clone method so that we can change the value later. How come you can say its immutable ?

Reply
Prakash says

NOVEMBER 30, 2017 AT 3:14 AM

I believe, you are talking about getter method.

public HashMap getTestMap() {
//return testMap;
return (HashMap) testMap.clone();
}

I believe, It should return a copy by deep cloning.

Reply
Prakash says

NOVEMBER 30, 2017 AT 3:17 AM

I believe, In this example, Shallow copy would also work fine as long as we are storing immutable String object in HashMap.

In case, if we need to store mutable object in HashMap. We should do deep cloning

Reply
Pratyush says

APRIL 6, 2017 AT 6:27 AM

I it necessary to have variable as final??
We can achive it without it also,
there is no statement to change varable.

Reply
Rahul says

APRIL 1, 2017 AT 11:18 AM

When you make the field final, Why making the variable private is mandate?

Reply
tabish says

MARCH 1, 2017 AT 3:53 AM

hi pankaj,

i love to read your blog. here i found a hack at main() using a reflection how to prevent it.

Class mc = ce.getClass();
Field fs = mc.getDeclaredField(“name”);

fs.setAccessible(true);

fs.set(ce, “hacked”);

System.out.println(“ce name after reflection hack:”+ce.getName());

Reply
shashank says

MARCH 22, 2017 AT 7:39 AM

You cannot do this since name is final

Reply
Umang Gupta says

AUGUST 13, 2018 AT 12:55 AM

It does work.

Reply
WAA says

JANUARY 12, 2017 AT 9:01 PM

Hi Pankaj
I didn’t Understood the System.out.println(h1 == ce.getTestMap()) answer is False.
Can you Please explain why it is false.

Reply
Ankush K says

APRIL 20, 2017 AT 12:23 AM

On this statement we are just checking the reference of h1 is pointing to the one that of our final class TestMap reference, which in this case is no, because we have made a new copy of h1 hashmap and copied it in TempHashMap which is an completely new Object, & then the reference of this temp map is assign to TestHashMap.

Hence this reference are pointing to, two different Object all together.

Reply
Bektur Toktosunov says

DECEMBER 28, 2015 AT 10:44 AM

Thanks for great article!

Can we use testMap.clone() in the constructor instead of going through all of the map items with Iterator?

Reply
Vineet kaushik says

DECEMBER 22, 2015 AT 12:00 AM

Very easy to understand and useful post!!

Reply
Vijay Nandwana says

DECEMBER 18, 2015 AT 3:02 AM

Thank you Pankaj. I’m a big fan of your writing skills. You cover every details and explain concepts in easy to understand language. Thanks again.

Reply
Ajaz says

SEPTEMBER 14, 2015 AT 12:07 PM

private final String name; …Why this is final …String is already final …do we need to declare it again..???

Reply
Vinay says

FEBRUARY 5, 2017 AT 10:40 PM

“String is already final.” – yes , it is , but that final is at class level which means you can’t extend the String class, while that “private final String ” is for variable , so that we cant change the value of that object once initialized.

Reply
Bijoy says

MARCH 14, 2015 AT 9:11 AM

I can modify the object using ce.testMap.put(“10”, “ten”);

Output:

Performing Deep Copy for Object initialization
true
false
ce id:10
ce name:original
ce testMap:{2=second, 1=first}
ce id after local variable change:10
ce name after local variable change:original
ce testMap after local variable change:{2=second, 1=first, 10=ten}
ce testMap after changing variable from accessor methods:{2=second, 1=first, 10=ten}

Reply
parasuram tanguturu says

OCTOBER 16, 2016 AT 8:45 AM

how to overcome this case;
ce.testMap.put(“10”, “ten”);

//ce testMap after local variable change:{2=second, 1=first, 10=ten}
//ce testMap after changing variable from accessor methods:{2=second, 1=first, 10=ten}

Reply
JavaRocker says

APRIL 27, 2017 AT 12:04 AM

testMap is private variable and out side class it wont be available.

Its just a Example code that is why in main you are able to do ce.testMap but in real application you wont be as generally you dont do such operations in POJO class.

Reply
ahmed says

MARCH 7, 2015 AT 1:34 AM

thanx
i disable ABP for u

Reply
Pankaj says

MARCH 7, 2015 AT 9:50 AM

Thanks Ahmed, I appreciate it.

Reply
S says

FEBRUARY 12, 2015 AT 3:26 AM

Excellent post!!

Reply
mahi says

FEBRUARY 1, 2015 AT 7:34 PM

Can you please describe no 6. more deeply.I am not able to understand it.

Reply
Rais Alam says

NOVEMBER 22, 2014 AT 6:45 AM

Hi Pankaj,

It was a great learning about creating Immutable objects.If you are performing step 5 and 6 then step 4 is not required I guess. You are not storing or returning original reference of HashMap, You are using clone concept for that, Hence as a result client application have no way to reassign new object to declared HaspMap.

Please correct me If I am missing some thing

Thanks& Regards
Rais Alam

Reply
Ramakant says

NOVEMBER 10, 2014 AT 12:05 PM

Should not be String name declared as a not final? Its not mutable anyway.

Reply
Marwen says

AUGUST 18, 2014 AT 2:40 AM

Thanks for the detailed tutorial, well written and the flow goes exactely to showing up almost the need of every instruction in the code 🙂
One side question, even if I know we are talking about Objects immutability,but what about the other instance variables you introucted in the FinalClassExample (id, name)?
Is there any way to make them immutable?

Reply
Pankaj says

AUGUST 18, 2014 AT 5:04 AM

int and String both are already immutable, since there are no setter methods for them. For any other class variables, you should return a deep copy of the variable to avoid mutability.

Reply
Mirey says

SEPTEMBER 19, 2013 AT 6:38 PM

Thanks, you know it and you know how to explain it too! I will definitely read more of your articles 🙂

Reply
Anish Sneh says

JUNE 2, 2012 AT 9:59 AM

Thanks mate, great details..

— Anish Sneh

Reply
Pankaj says

OCTOBER 17, 2012 AT 6:47 PM

Thanks for the kind words.

Reply
Łomża Zuhlke says

MAY 5, 2011 AT 5:43 PM

It’s super webpage, I was looking for something like this

Reply
whaley says

DECEMBER 23, 2010 AT 12:35 PM

Out of curiosity, why the requirement to have the class be marked as final so as not to be extended? What does disallowing subclasses actually provide in terms of allowing objects of this type to be immutable?

Further, you don’t have to mark fields as private only just so long as you can guarantee that all constructor’s of the class properly initialize all of the fields.

As a side note, you *can* have setters, but with the nuance that instead of changing an internal field, what the setter really does is specify a return type of the class the method is on, and then behind the scenes creates a new object using a constructor that accepts all internal fields, using the internally held state in for all params with the exception of the field represented by the setter called since you want the new object to have that field updated.

Reply
Pankaj says

DECEMBER 23, 2010 AT 3:52 PM

If the class is not marked as final then its function can be overridden in the subclass either accidentally or intentionally. So its more related to keep the object secure. For this either all the getter methods can be made final or the class itself – this is again a design decision and depends on the requirement.

Again if the fields wont be private then client application can override the value. Make the HashMap as public in the code and run the below code to see yourself.

FinalClassExample fce = new FinalClassExample(1,"", new HashMap());
System.out.println(fce.testMap);
HashMap hm = fce.testMap;
hm.put("1", "1");
System.out.println(fce.testMap);

Having a setter function will give the feeling that the actual object has been modified whereas internally creating a new object. Its better to client application know that its immutable (like String).

Reply
Hamlet D'Arcy says

DECEMBER 23, 2010 AT 9:02 AM

In Groovy you can annotate the class as @Immutable and get /almost/ similar results to the scala example without all the boilerplate. IMHO Scala is better for it’s immutable support though.

Also, don’t forget that Java Date, Dimension, and other JDK classes are not immutable as well, so you need to make defensive copies of those classes as well.

Reply
Pankaj says

DECEMBER 23, 2010 AT 9:48 AM

Exactly, for all the mutable objects we need to return the defensive copy rather than same object reference.
Have to dig into Scala now.

Reply
John says

DECEMBER 23, 2010 AT 8:30 AM

why don’t you just do this:

import static java.util.Collections.unmodifiableMap;

public final class FinalClassExample {

...

private final Map testMap;

public FinalClassExample(int i, String n, Map m){
id = i;
name = n;
testMap = unmodifiableMap(new HashMap (m));
}

public Map getTestMap() {
return testMap;
}

...
}

Reply
Pankaj says

DECEMBER 23, 2010 AT 9:30 AM

In this case, when we will get the testMap from getTestMap() function, we will not be allowed to modify it and it will throw exception. Also in that case again we are passing the reference and the values will change accordingly.
Try executing this class:

package com.journeldev.java;

import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;

public final class FinalClassExample1 {

private final Map testMap;

public Map getTestMap() {
return testMap;
}

public FinalClassExample1(Map hm) {
this.testMap = unmodifiableMap(hm);
}

public static void main(String[] args) {
HashMap h1 = new HashMap();
h1.put("1", "first");
h1.put("2", "second");
FinalClassExample1 ce = new FinalClassExample1(h1);
System.out.println("ce testMap:" + ce.getTestMap());
h1.put("3", "third");
System.out.println("ce testMap after local variable change:"+ ce.getTestMap());
Map hmTest = ce.getTestMap();
hmTest.put("4", "new");
System.out.println("ce testMap after changing variable from accessor methods:"+ ce.getTestMap());

}

}

Output will be:
ce testMap:{2=second, 1=first}
ce testMap after local variable change:{3=third, 2=second, 1=first}
Exception in thread “main” java.lang.UnsupportedOperationException
at java.util.Collections$UnmodifiableMap.put(Collections.java:1285)
at com.journeldev.java.FinalClassExample1.main(FinalClassExample1.java:33)

Reply
Steve says

DECEMBER 23, 2010 AT 6:25 PM

Pankaj,

You should not be allowed to modify a map you get from getTestMap. Objects coming from an immutable object, I would expect to also be immutable so you can not change the internals of the object after it is created. A mutable map may imply that the objects map is changed. Sure you can document that the object returned is new, but being defensive upfront seems better.

Another alternative is to never return collections. Instead, define a forEach method that takes a function:

public void forEach(Function fn) {
for(Thing t : Iterable collection) {
fn.apply(t);
}
}

Reply
Pankaj says

DECEMBER 24, 2010 AT 6:23 AM

I am not sure why the map returned from getTestMap method should not be allowed to modify. Suppose the list contains some 1000 items and client wants to add 5 more to make their own immutable object instance, in this case its better to let them modify the returned object and do whatever they want on it.
Usually, we have setter methods to change the state of object, so just by changing the returned object you should not think that the object state has been changed because the internal implementation is not known to us(as a client).

Reply
John says

DECEMBER 24, 2010 AT 9:38 AM

you didn’t notice that I wrote:

this.testMap = modifiableMap(new HashMap (m))

so you couldn't change the inner map after construction.

and I agree with Steve that the map returned by getTestMap() should not be modifiable as this would give the false impression that you're actually changing the immutable object.

in that case I would prefer doing something like this:

FinalClassExample example = new FinalClassExample(...);
Map newMap = new HashMap(example.getTestMap());
newMap.put("hello", "world");

Reply
Pankaj says

DECEMBER 24, 2010 AT 3:27 PM

Ok agreed that this will work but then its not generic. What if its some generic mutable class like Date or may be user defined class where you don’t have this feature. This example is intended to provide a generic way to create immutable classes.

As for changing the object state, that is why we have setter methods and I still prefer it doing like that… but again it depends on your application requirements and design.

Reply
Shantanu Kumar says

DECEMBER 23, 2010 AT 4:11 AM

Shouldn’t it be “shallow copy” instead of “swallow copy” unless I am missing something?

Reply
Pankaj says

DECEMBER 23, 2010 AT 6:35 AM

Thanks for pointing out the typo error. Corrected now to shallow copy.

Reply
Ken says

DECEMBER 23, 2010 AT 12:11 AM

… and here is a translation of the entire exercise into Scala, except without the wasteful copying and cloning, and with correct equals and hashCode methods:

case class FinalClassExample(id: Int, name: String, testMap: Map[String,String])

Reply
Pankaj says

DECEMBER 23, 2010 AT 6:42 AM

I didn’t understood what are you trying to point here?

Reply
Paweł Chłopek says

DECEMBER 23, 2010 AT 8:03 AM

I belive that he was trying to point out that Scala lets you create immutable classes in more concise way. I’m new to Scala myself and already I love and often use these one liners. Less code less opportunities to make mistakes 🙂

Reply
Pankaj says

DECEMBER 23, 2010 AT 8:45 AM

Thats a completely new thing to me… Got something new to learn now… will dig into this soon 🙂

Reply
Comment Policy:Please submit comments to add value to the post. Comments like "Thank You" and "Awesome Post" will be not published. If you want to post code then wrap them inside <pre> tags. For example <pre>class Foo { }</pre>.
If you want to post XML content, then please escape < with &lt; and > with &gt; otherwise they will not be shown properly.

Leave a Reply
Your email address will not be published. Required fields are marked *

Comment

Name *

Email *

 
Instantly Search Tutorials...
 

Core Java Tutorial
Java 11 Tutorials
Java 10 Tutorials
Java 9 Tutorials
Java 8 Tutorials
Java 7 Tutorials
Core Java Basics
OOPS Concepts
Data Types and Operators
String Manipulation
Java Arrays
Annotation and Enum
Java Collections
Java IO Operations
Java Exception Handling
MultiThreading and Concurrency
Regular Expressions
Advanced Java Concepts
Recommended Tutorials
Java Tutorials
Java EE Tutorials
© 2019 · Privacy Policy · Powered by WordPress