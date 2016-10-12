package com.hive.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.io.ByteArrayOutputStream;

/**
 * <p>
 * Title: ZipHelper类
 * </p>
 * <p>
 * Description: 对文件和目录进行压缩和解压缩
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: winpath
 * </p>
 * @author 王彬
 * @version 1.0000
 */
public final class ZipHelper
{
    /**
     * 类禁止外部实例化
     */
    private ZipHelper()
    {
        //donone
    }

    /**
     * 压缩指定的文件
     * @param as_SrcFileName 被压缩的文件（包含名称及路径）
     * @param as_DesFileName 压缩后的文件名称（包含路径）
     * @throws java.lang.IllegalArgumentException 如果参数无效
     * @throws java.lang.Exception 包含其它任何异常
     */
    public static void uf_CompressFile(String as_SrcFileName,String as_DesFileName) throws IllegalArgumentException,Exception
    {
        if (as_SrcFileName == null || as_SrcFileName.length() == 0) { throw new IllegalArgumentException("参数为空"); }

        if (as_DesFileName == null || as_DesFileName.length() == 0) { throw new IllegalArgumentException("参数为空"); }

        BufferedInputStream bis = null;
        ZipOutputStream zos = null;
        long nFileLen = 0;
        int nLen = 0;
        ZipEntry ze = null;
        byte[] buf = new byte[1024];

        try
        {
            File srcFile = new File(as_SrcFileName);
            nFileLen = srcFile.length();
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            zos = new ZipOutputStream(new FileOutputStream(as_DesFileName));
            ze = new ZipEntry(srcFile.getName());
            zos.putNextEntry(ze);

            while (true)
            {
                nLen = bis.read(buf);

                if (nLen > 0)
                {
                    zos.write(buf,0,nLen);
                    nFileLen -= nLen;
                }
                else
                {
                    break;
                }
            }

            if (nFileLen != 0) { throw new IllegalArgumentException("压缩时发生错误"); }
        }
        finally
        {
            if (bis != null)
            {
                bis.close();
            }

            if (zos != null)
            {
                zos.flush();
                zos.closeEntry();
                zos.close();
            }
        }
    }

    /**
     * 压缩指定的目录
     * @param as_SrcDirectoryName 被压缩的目录
     * @param as_DesFileName 压缩后的文件名称（包含路径）
     * @throws java.lang.IllegalArgumentException 如果参数无效
     * @throws java.lang.Exception 包含其它任何异常
     */
    public static void uf_CompressDirectory(String as_SrcDirectoryName,String as_DesFileName) throws IllegalArgumentException,Exception
    {
        if (as_SrcDirectoryName == null || as_SrcDirectoryName.length() == 0) { throw new IllegalArgumentException("参数为空"); }

        if (as_DesFileName == null || as_DesFileName.length() == 0) { throw new IllegalArgumentException("参数为空"); }

        org.apache.tools.zip.ZipOutputStream zos = null;
        BufferedInputStream bis = null;

        try
        {
            zos = new org.apache.tools.zip.ZipOutputStream(new FileOutputStream(as_DesFileName));
            zos.setEncoding("utf-8");
            ArrayList list = new ArrayList();
            uf_ListFile(as_SrcDirectoryName,"",list);
            int i = 0;
            ListItem item;

            while (i < list.size())
            {
                item = (ListItem)list.get(i);

                //------------------------------------------------------------------------
                //这段代码可以写为一个私有方法
                byte[] buf = new byte[1024];
                int nLen = 0;
                File file = new File(item.strPath);
                bis = new BufferedInputStream(new FileInputStream(file));
                org.apache.tools.zip.ZipEntry e = null;
                long nFileLen = file.length();

                if (item.strEntry != "")
                {
                    e = new org.apache.tools.zip.ZipEntry((item.strEntry + File.separator + file.getName()));      
                }
                else
                {
                    e = new org.apache.tools.zip.ZipEntry(file.getName());
                }

                zos.putNextEntry(e);

                while (true)
                {
                    nLen = bis.read(buf);

                    if (nLen > 0)
                    {
                        zos.write(buf,0,nLen);
                        nFileLen -= nLen;
                    }
                    else
                    {
                        break;
                    }
                }

                bis.close();
                bis = null;
                zos.closeEntry();

                if (nFileLen != 0) { throw new IllegalArgumentException("压缩时发生错误"); }
                //------------------------------------------------------------------------

                i++;
            }

            zos.flush();
            zos.close();
            zos = null;
        }
        finally
        {
            if (bis != null)
            {
                bis.close();
            }

            if (zos != null)
            {
                zos.flush();
                zos.close();
            }
        }
    }

    /**
     * 解压指定的文件
     * @param as_SrcFileName 被解压的文件名称（包含路径）
     * @param as_DesSaveDirectory 解压后文件的保存目录
     * @throws java.lang.IllegalArgumentException 如果参数无效
     * @throws java.lang.Exception 包含其它任何异常
     */
    public static void uf_DecompressFile(String as_SrcFileName,String as_DesSaveDirectory) throws IllegalArgumentException,Exception
    {
        if (as_SrcFileName == null || as_SrcFileName.length() == 0) { throw new IllegalArgumentException("参数为空"); }

        if (as_DesSaveDirectory == null || as_DesSaveDirectory.length() == 0) { throw new IllegalArgumentException("参数为空"); }

        ZipInputStream zis = null;
        ZipEntry ze = null;
        BufferedOutputStream bos = null;
        byte[] buf = new byte[1024];
        int nLen = 0;

        try
        {
            File srcFile = new File(as_SrcFileName);
            zis = new ZipInputStream(new FileInputStream(srcFile));

            while ((ze = zis.getNextEntry()) != null)
            {
                String entryName = ze.getName();
                File unzipFile = new File(as_DesSaveDirectory + File.separator + entryName);
                uf_MakeDirIfNotExist(unzipFile.getPath());
                bos = new BufferedOutputStream(new FileOutputStream(unzipFile));

                while (true)
                {
                    nLen = zis.read(buf);

                    if (nLen > 0)
                    {
                        bos.write(buf,0,nLen);
                    }
                    else
                    {
                        break;
                    }
                }

                bos.close();
                zis.closeEntry();
            }

            bos.flush();
            bos.close();
            bos = null;
            zis.close();
            zis = null;
        }
        finally
        {
            if (bos != null)
            {
                bos.flush();
                bos.close();
            }

            if (zis != null)
            {
                zis.close();
            }
        }
    }

    /**
     * 压缩指定的字节数组
     * @param as_SrcInfo 被压缩的字节数组
     * @return 压缩后的字节数组
     * @throws java.lang.IllegalArgumentException 如果参数无效
     * @throws java.lang.Exception 包含其它任何异常
     */
    public static byte[] uf_CompressInfo(byte[] as_SrcInfo) throws IllegalArgumentException,Exception
    {
        if (as_SrcInfo == null || as_SrcInfo.length == 0) { throw new IllegalArgumentException("参数为空"); }

        ByteArrayOutputStream baos = null;
        Deflater df = null;

        try
        {
            df = new Deflater();
            df.setInput(as_SrcInfo);
            df.finish();

            baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            while (!df.finished())
            {
                int nRead = df.deflate(buf);
                baos.write(buf,0,nRead);
            }

            baos.flush();
            baos.close();
            df.end();
            return baos.toByteArray();
        }
        finally
        {
            if (baos != null)
            {
                baos.flush();
                baos.close();
            }

            if (df != null)
            {
                df.end();
            }
        }
    }

    /**
     * 解压指定的字节数组
     * @param as_SrcInfo 被解压的字节数组
     * @return 解压后的字节数组
     * @throws java.lang.IllegalArgumentException 如果参数无效
     * @throws java.lang.Exception 包含其它任何异常
     */
    public static byte[] uf_DecompressInfo(byte[] as_SrcInfo) throws IllegalArgumentException,Exception
    {
        if (as_SrcInfo == null || as_SrcInfo.length == 0) { throw new IllegalArgumentException("参数为空"); }

        ByteArrayOutputStream baos = null;
        Inflater inf = null;

        try
        {
            inf = new Inflater();
            inf.setInput(as_SrcInfo);

            baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            while (!inf.finished())
            {
                int nRead = inf.inflate(buf);
                baos.write(buf,0,nRead);
            }

            baos.flush();
            baos.close();
            inf.end();
            return baos.toByteArray();
        }
        finally
        {
            if (baos != null)
            {
                baos.flush();
                baos.close();
            }

            if (inf != null)
            {
                inf.end();
            }
        }
    }

    /**
     * 列出目录下的所有文件
     * 
     * @param as_DirName
     *            源目录
     * @param as_Entry
     *            入参，调用时设为空字符串""，在递归调用时保存每个文件的条目信息
     * @param ao_ArrayList
     *            出参，保存目录下所有文件的信息
     */
    private static void uf_ListFile(String as_DirName,String as_Entry,ArrayList ao_ArrayList)
    {
        String strEntry = "";
        int i = 0;

        File file = new File(as_DirName);
        String[] listFile = file.list();

        if (file.isDirectory())
        {
            while (i < listFile.length)
            {
                File ff = new File(file.getAbsolutePath() + File.separator + listFile[i]);

                if (ff.isDirectory())
                {
                    if (ff.getName().compareToIgnoreCase(".") != 0 && ff.getName().compareToIgnoreCase("..") != 0)
                    {
                        if (as_Entry.compareToIgnoreCase("") != 0) 
                        	strEntry = as_Entry + File.separator + ff.getName();
                        else strEntry = ff.getName();
                        	uf_ListFile(ff.getAbsolutePath(),strEntry,ao_ArrayList);
                    }
                }
                else
                {
                    ListItem li = new ListItem();
                    li.strEntry = as_Entry;
                    li.strPath = file.getAbsoluteFile() + File.separator + ff.getName();
                    ao_ArrayList.add(li);
                }

                i++;
            }
        }
        else
        {
            ListItem li = new ListItem();
            li.strEntry = as_Entry;
            li.strPath = file.getAbsolutePath() + File.separator + file.getName();
            ao_ArrayList.add(li);
        }
    }

    private static void uf_MakeDirIfNotExist(String as_FileName)
    {
        String dir = as_FileName.substring(0,as_FileName.lastIndexOf(File.separator));
        File file = new File(dir);

        if (!file.isDirectory())
        {
            file.mkdirs();
        }
    }

    private static class ListItem
    {
        /**
         * 输入
         */
        public String strEntry;

        /**
         * 路径
         */
        public String strPath;
    }
}
