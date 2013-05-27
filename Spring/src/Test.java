
public class Test {

	public static int power(int n, int x){
		System.out.println("n="+n+", x="+x);
	    if (x==0){
	    	System.out.println("Returning condition 0");
	        return 1;
	    }
	    if(x== 1){
	    	System.out.println("Returning condition 1");
	        return n;
	    }
	    if(x%2 == 0){
	    	System.out.println("x moduler 2 ");
	        return power(n*n,x/2);
	    }
	    else{
	        return (power(n*n,x/2)*n);
	    }
	 
	}
	public static void main(String[] args) {
		System.out.println(power(5,5));
	}

}
