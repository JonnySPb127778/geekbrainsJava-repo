public class TestedMethods {

    public int[] copyArrayAfterLast4(int[] arrInp){
        for (int i = arrInp.length - 1; i >= 0; i--) {
            if(arrInp[i] == 4){
                int[] arrOut = new int[arrInp.length - 1 - i];
                if(i==arrInp.length - 1) return arrOut;
                for (int j = 0, k = i + 1; j < arrOut.length; j++, k++)
                    arrOut[j] = arrInp[k];
                return arrOut;
            }
        }
        throw new RuntimeException();
    }

    public boolean arrayOf1and4(int[] arr){
        boolean bOne  = false;
        boolean bFour = false;

        for (int item: arr) {
            if(item != 1 && item != 4) return false;

            if(item == 1) bOne  = true;
            else          bFour = true;
        }
        return bOne && bFour;
    }


}
