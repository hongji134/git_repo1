package anno1;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MTest {

	public static void main(String[] args) {
		GenericXmlApplicationContext context = 
				new GenericXmlApplicationContext("anno1/bean.xml");
		
		MyMessage my1 = (MyMessage) context.getBean("myMessage");
		System.out.println(my1.getMessage() + "   " + my1);
		
		MyMessage my2 = (MyMessage) context.getBean("myMessage");
		System.out.println(my2.getMessage() + "   " + my2);
		
		context.close();
	}

}
