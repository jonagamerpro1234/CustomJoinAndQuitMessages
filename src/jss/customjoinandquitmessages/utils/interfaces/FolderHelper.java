package jss.customjoinandquitmessages.utils.interfaces;

import java.io.IOException;
import java.util.List;

public interface FolderHelper {

	public String getFolderPath();	
	public List<String> getJarFileList() throws IOException;
	
}
