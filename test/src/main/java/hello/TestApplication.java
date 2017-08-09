package hello;

/**
 * Created by ling on 17/8/4.
 */
public class TestApplication {

    public static void main(String[] args){
        //10-a,11-b,12-c,13-d,14-e,15-f
        byte b = Integer.valueOf("AA",16).byteValue();
        int a = 0xAA;

        boolean dataTypeNums = (b&0xFF)==0x33;
        int c = b << 4;

        String mes = "N2-123333";
        int n = mes.lastIndexOf("-")+1;
        String pre = mes.substring(0,n);
        String pos = mes.substring(n);

        System.out.println(">>>>>> n <<<<<<<<"+((b&0xFF)==a));
        System.out.println(">>>>>> pre <<<<<<<<"+pre);
        System.out.println(">>>>>> pos <<<<<<<<"+pos);
        System.out.println(">>>>>> mes <<<<<<<<"+pre+pos);
        System.out.println(">>>>>> b  <<<<<<<<"+b);
        System.out.println(">>>>>> dataTypeNums  <<<<<<<<"+dataTypeNums);
        System.out.println(">>>>>>A  <<<<<<<<"+a);

    }
}
