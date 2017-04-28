package AdvanceInterface;


import java.io.*;

public class Files {
    private String fileName;
    private File file;

    public Files(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    private void write(String text) {
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

    public String read() throws FileNotFoundException {
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();

        try {
            existFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
				sb.append(System.lineSeparator());
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        //Возвращаем полученный текст с файла
        return sb.toString();
    }


    public void update(String newText) throws FileNotFoundException {
        try {
            existFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String oldFile = read();
        sb.append(oldFile);
        sb.append(newText);
        write(sb.toString());
    }

    public double checkFileSize() {
		double f = file.length();
        return f;

    }

    private void existFile() throws IOException {
        if(!file.exists()){
            file.createNewFile();
        }
    }

	public void delete() {
		if(file.exists()) file.delete();
	}

}


