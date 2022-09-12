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

    public static class valida3x3 implements Runnable{
        int linha;
        int coluna;
        int count;

        public valida3x3(int linha, int coluna, int count) {
            this.linha = linha;
            this.coluna = coluna;
            this.count = count;
        }

        @Override
        public void run() {
            boolean[] validaCelula = new boolean[9];
            int num;
            for (int i= linha; i < linha+3; i++){
                for (int j = coluna; j < coluna+3; j++) {

                    num = matriz[i][j];
                    if (num < 1 || num > 9 || validaCelula[num - 1]) {
                        return;
                    } else if (!validaCelula[num - 1]) {
                        validaCelula[num - 1] = true;
                    }

                }
            }
            validaSudoku[count] = true;
        }
    }

    public static void main(String[] args) {
        int threadIndex = 0;
        int count = 18;
        Thread threads[] = new Thread[27];
        validaSudoku = new boolean[27];

        //threads das linhas
        for (int i = 0; i < 9; i++){
            threads [threadIndex++] = new Thread(new validaLinha(i));
        }
        //threads das colunas
        for (int j = 0; j < 9; j++){
            threads [threadIndex++] = new Thread(new validaColuna(j));
        }
        //threads das matriz 3x3
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if ( i%3 == 0 && j%3 ==0) {
                    threads[threadIndex++] = new Thread(new valida3x3(i, j, count++));
                }

            }
        }

        //starta todas as thread
        for (int i = 0; i < threads.length; i++){
            threads[i].start();
        }

        //join threads
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //mostra array
        for (int i = 0; i < validaSudoku.length; i++) {
            System.out.println( i+ ": " + validaSudoku[i]);
        }
    }

}
