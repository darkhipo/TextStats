
package TextStats;

import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class Main{ 
    //http://stackoverflow.com/questions/5176771/sort-hashtable-by-values
    public static ArrayList<Map.Entry<String, Integer>> sortValue(Hashtable<String, Integer> t){
        ArrayList<Map.Entry<String, Integer>> l = new ArrayList( t.entrySet() );
        Collections.sort(l, new Comparator<Map.Entry<String, Integer>>(){
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
        }});

        return l;
    }
    public static void main( String args[] ){
        Hashtable <String, Integer> ht = new Hashtable<String, Integer>();
        String inPath = args[0];
        Integer wc = 0;
        Integer ml = 0;

        try{
            BufferedReader br = new BufferedReader( new FileReader( inPath ) );
            try {
                String line;
                while ( ( line = br.readLine() ) != null ){
                    line = line.toLowerCase().replaceAll("\\p{P}|\\d+|\\|", " ");
                    StringTokenizer st = new StringTokenizer( line );
                    while( st.hasMoreTokens() ){
                        String token = st.nextToken();
                        if ( ht.containsKey( token ) ){
                            Integer curr = ht.get( token );
                            ht.put( token, curr + 1 );
                        }
                        else{
                            ml = Math.max( ml, token.length() );
                            ht.put( token, 1 );
                        }
                        wc++;
                    }
                } 
            } finally {
                br.close();
            }
        }
        catch ( FileNotFoundException e ){
            e.printStackTrace();
        }
        catch ( IOException e ){
            e.printStackTrace();
        }
        ArrayList<Map.Entry<String, Integer>> l = sortValue( ht );
        for ( int i=0; i < l.size(); i++ ) {
            String token = l.get(i).getKey();
            Integer count = l.get(i).getValue();
            System.out.printf( "%d\t%" + ml + "s\t%d\t%.4f\n", i, token, count, count.doubleValue() / wc.doubleValue() ) ;
        }
        System.out.flush();
    }
}
