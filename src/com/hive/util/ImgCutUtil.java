package com.hive.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImgCutUtil
{
  public static void main(String[] args)
  {
    cut(100, 100, 120, 120, "D:\\MomanCamera_20131114_083511.jpg", "D:\\1.jpg");
  }

  public static void cut(int x1, int y1, int width, int height, String sourcePath, String descPath)
  {
    FileInputStream is = null;
    ImageInputStream iis = null;
    try {
      is = new FileInputStream(sourcePath);
      String fileSuffix = sourcePath.substring(sourcePath
        .lastIndexOf(".") + 1);
      Iterator it = 
        ImageIO.getImageReadersByFormatName(fileSuffix);
      ImageReader reader = (ImageReader)it.next();
      iis = ImageIO.createImageInputStream(is);
      reader.setInput(iis, true);
      ImageReadParam param = reader.getDefaultReadParam();
      Rectangle rect = new Rectangle(x1, y1, width, height);
      param.setSourceRegion(rect);
      BufferedImage bi = reader.read(0, param);
      ImageIO.write(bi, fileSuffix, new File(descPath));
      //这里为了方便门户网站显示奖品图片，同时也把奖品图片保存到前台项目里面，
      String frontPath = descPath.replace("zxt-manage", "zxt");
      ImageIO.write(bi, fileSuffix, new File(frontPath));
    } catch (Exception ex) {
      ex.printStackTrace();

      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (iis != null)
        try {
          iis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
    finally
    {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (iis != null)
        try {
          iis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }
}