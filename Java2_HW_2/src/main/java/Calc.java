public class Calc {
    public long sum(String[][] data) {
        if (data == null ) {
            String msg = String.format("Null array size is invalid. Array size should be 4x4!",
                    data.length, data[0].length );
        }

        if (data.length == 0 ) {
            String msg = String.format("%d x %d - incorrect array size entered. Array size should be 4x4!",
                    0, 0 );
            throw new ArraySizeException(msg);
        }

        if (data.length != 4) {
            String msg = String.format("%d x %d - incorrect array size entered. Array size should be 4x4!",
                    data.length, data[0].length );
            throw new ArraySizeException(msg);
        }

        for(String[] arr_str : data ) {
            if (arr_str.length != 4) {
                String msg = String.format("%d x %d - incorrect array size entered. Array size should be 4x4!",
                        data.length, data[0].length);
                throw new ArraySizeException(msg);
            }
        }

        long sum = 0;
        for(String[] arr_str : data ){
            for(String str :  arr_str)
                try {
                    sum += Integer.parseInt(str);
                } catch (Exception exc) {
                    throw new ArrayDataException("Data could not parsed to int");
                }
        }
        return sum;
    }
}
