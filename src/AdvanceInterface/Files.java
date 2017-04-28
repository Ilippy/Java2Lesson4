package AdvanceInterface;


import java.io.*;

public class Files {
    private String fileName;
    private File file;

    public Files(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    private void write(String fileName, String text) {
        //Определяем файл
        File file = new File(fileName);

        try {
            //проверяем, что если файл не существует то создаем его
            if (!file.exists()) {
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(text);
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String read(String fileName) throws FileNotFoundException {
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();

        exists(fileName);

        try {
            //Объект для чтения файла в буфер
            BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                //В цикле построчно считываем файл
                String s;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                    sb.append(System.lineSeparator());
                }
            } finally {
                //Также не забываем закрыть файл
                br.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Возвращаем полученный текст с файла
        return sb.toString();
    }

    private void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
    }

    public void update(String nameFile, String newText) throws FileNotFoundException {
        exists(fileName);
        StringBuilder sb = new StringBuilder();
        String oldFile = read(nameFile);
        sb.append(oldFile);
        sb.append(newText);
        write(nameFile, sb.toString());
    }

    public double checkFileSize() {
        /*try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return file.length();

    }


}


