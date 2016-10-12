package com.hive.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;

public class Thumbnail
{
  public static void saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int height, boolean equalProportion)
    throws Exception
  {
    String imgType = "JPEG";
    if (fromFileStr.toLowerCase().endsWith(".png")) {
      imgType = "PNG";
    }
    File fromFile = new File(fromFileStr);
    File saveFile = new File(saveToFileStr);
    BufferedImage srcImage = ImageIO.read(fromFile);
    if ((width > 0) || (height > 0)) {
      srcImage = resize(srcImage, width, height, equalProportion);
    }
    ImageIO.write(srcImage, imgType, saveFile);
  }

  public static BufferedImage resize(BufferedImage source, int targetW, int targetH, boolean equalProportion)
  {
    int type = source.getType();
    BufferedImage target = null;
    double sx = targetW / source.getWidth();
    double sy = targetH / source.getHeight();

    if (equalProportion) {
      if (sx > sy) {
        sx = sy;
        targetW = (int)(sx * source.getWidth());
      } else {
        sy = sx;
        targetH = (int)(sx * source.getHeight());
      }
    }
    if (type == 0) {
      ColorModel cm = source.getColorModel();
      WritableRaster raster = cm.createCompatibleWritableRaster(targetW, 
        targetH);
      boolean alphaPremultiplied = cm.isAlphaPremultiplied();
      target = new BufferedImage(cm, raster, alphaPremultiplied, null);
    } else {
      target = new BufferedImage(targetW, targetH, type);
      Graphics2D g = target.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_RENDERING, 
        RenderingHints.VALUE_RENDER_QUALITY);
      g.drawRenderedImage(source, 
        AffineTransform.getScaleInstance(sx, sy));
      g.dispose();
    }
    return target;
  }

  public static void main(String[] args) {
    try {
      saveImageAsJpg("D://scal-before.jpg", 
        "D://scal-after.jpg", 480, 480, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}