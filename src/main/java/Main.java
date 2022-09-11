public class Main {
    private static int  matriz[][] = {
            {6, 2, 4, 5, 3, 9, 1, 8, 7},
            {5, 1, 9, 7, 2, 8, 6, 3, 4},
            {8, 3, 7, 6, 1, 4, 2, 9, 5},
            {1, 4, 3, 8, 6, 5, 7, 2, 9},
            {9, 5, 8, 2, 4, 7, 3, 6, 1},
            {7, 6, 2, 3, 9, 1, 4, 5, 8},
            {3, 7, 1, 9, 5, 6, 8, 4, 2},
            {4, 9, 6, 1, 8, 2, 5, 7, 3},
            {2, 8, 5, 4, 7, 3, 9, 1, 6}
    };
    public static int NUM_THREAD = 11;
    public static boolean[] validaSudoku;

    public static class validaLinha implements Runnable {
        int linha;
        public validaLinha(int linha) {
            this.linha = linha;
        }

        @Override
        public void run() {
            boolean[] validaCelula = new boolean[9];
            int num;
            for (int i= 0; i < 9; i++){
                 num = matriz[linha][i];
                 if (num < 1 || num >9 || validaCelula[num-1]){
                     return;
                 }else if (!validaCelula[num-1]){
                     validaCelula[num-1] = true;
                 }
            }
            validaSudoku[linha] = true;
        }
    }

    public static class validaColuna implements Runnable{
        int coluna;
        public validaColuna(int coluna) {
            this.coluna = coluna;
        }

        @Override
        public void run() {
            boolean[] validaCelula = new boolean[9];
            int num;
            for (int i= 0; i < 9; i++){
                num = matriz[i][coluna];
                if (num < 1 || num >9 || validaCelula[num-1]){
                    return;
                }else if (!validaCelula[num-1]){
                    validaCelula[num-1] = true;
                }
            }
            validaSudoku[9+coluna] = true;
        }
    }



    public static void main(String[] args) {
        int threadIndex = 0;
        Thread threads[] = new Thread[18];
        validaSudoku = new boolean[18];

        for (int i = 0; i < 9; i++){
            threads [threadIndex++] = new Thread(new validaLinha(i));
        }
        for (int j = 0; j < 9; j++){
            threads [threadIndex++] = new Thread(new validaColuna(j));
        }


        for (int i = 0; i < threads.length; i++){
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < validaSudoku.length; i++) {
            System.out.println(validaSudoku[i]);
        }
    }
}
