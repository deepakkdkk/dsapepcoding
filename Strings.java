

public class Strings{

    //for lps (proper prefix ending at index i) (pattern + # + text)
    public int[] lps(String s){
        int[] lps = new int[s.length()];
        int i = 1;
        int len = 0;
        while(i < s.length()){
            if(s.charAt(i) == s.charAt(len)){
                len++;
                lps[i] = len;
                i++;
            }else{
                if(len > 0){
                    len = lps[len - 1];
                }else{
                    lps[i] = 0;
                    i++;
                }
            }
            
        }
        return lps;
    }

    //Z function (proper prefix starting at index i)  (pattern + # + text)
    public int[] zfunc(String s){
        int z[] = new int[s.length()];
        int l = 0, r = 0;
        for(int i = 1; i < s.length(); i++){
            if(i <= r){
                z[i] = Math.min(r - i + 1, z[i - l]);
            }
            while(i + z[i] < s.length() && (s.charAt(z[i]) == s.charAt(i + z[i]))){
                z[i]++;
            }
            if(i + z[i] - 1 > r){
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }
}