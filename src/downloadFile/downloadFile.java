package downloadFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class downloadFile {

	public static void main(String[] args) {
		dowload("/Users/bank21235/Documents/testcase/output/ccid_paperid.csv","/Users/bank21235/Documents/papers",0,2);
	}
	public static void dowload(String pathOfInputFile,String pathOfOutdir,int startPosition,int stopPosition) {
		String[] files = loadList(pathOfInputFile);
		for(int i = startPosition; i<files.length;i++) {
			if(!files[i].split(",")[1].trim().equals("NULL")) {
				System.out.println(files[i].split(",")[1].trim());
				downloadPaper(files[i].split(",")[1].trim(),pathOfOutdir);
				System.out.println((i+1)+"/"+files.length);
			}
			if(i==stopPosition)break;
		}
	}
	public static void downloadPaper(String paperID,String dirOutput) {
		File file = new File(dirOutput+"/"+paperID+".pdf");
		if(file.exists()) {
			System.out.println("This file has been download");
		}else {
			try {
				URL website = new URL("http://citeseerx.ist.psu.edu/viewdoc/download?doi="+paperID+"&rep=rep1&type=pdf");	
				FileUtils.copyURLToFile(website, file);
				//System.out.println("Download successfully: "+paperID);
			} catch (IOException e) {
				System.out.println("ERROR: "+e);
				File fail = new File(dirOutput+"/failDownload.txt");
				try {
					FileUtils.write(fail,paperID+": "+e+"\n", "UTF-8",true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	public static String[] loadList(String path) {
		File file = new File(path);
		String string = "";
		try {
			string = FileUtils.readFileToString(file,"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		string = string.replaceAll("\"", "");
		String[] temp = string.split("\n");
		return temp;
	}

}
