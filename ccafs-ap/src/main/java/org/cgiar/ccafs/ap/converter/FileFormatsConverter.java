package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.FileFormatManager;
import org.cgiar.ccafs.ap.data.model.FileFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class FileFormatsConverter extends StrutsTypeConverter {

  private FileFormatManager fileFormatManager;

  @Inject
  public FileFormatsConverter(FileFormatManager fileFormatManager) {
    this.fileFormatManager = fileFormatManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == List.class) {
      return fileFormatManager.getFileFormat(values);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    System.out.println("-----convertToString--------");
    List<FileFormat> ffArray = (List<FileFormat>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (FileFormat f : ffArray) {
      temp.add(f.getId() + "");
    }
    // TODO
    System.out.println(temp.toString());
    return temp.toString();
  }

}
