package com.iisi.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Component;

/**
 * 壓縮工具
 * 
 * @author 1109001
 *
 */
@Component
public class ZipComent {

	public void makeZip(File folder, File outFile) {

		File source = folder;

		File out = outFile;

		try {

			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
					outFile));

			// 取出來源目錄底下的所有 File 並新增至 ZIP 中
			for (File tmp : source.listFiles()) {
				checkFileType(tmp, zos, tmp.getName());
			}

			zos.finish();
			zos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 遞迴檢查 File 的屬性
	 * 
	 * @param file
	 * @param zos
	 * @param fileName
	 * @throws Exception
	 */
	private static void checkFileType(File file, ZipOutputStream zos,
			String fileName) throws Exception {
		if (file.isDirectory()) {
			for (File tmp : file.listFiles()) {
				checkFileType(tmp, zos, fileName + "/" + tmp.getName());
			}
		} else {
			addZipFile(file, zos, fileName);
		}
	}

	/**
	 * 新增 File 至 Zip 檔
	 * 
	 * @param file
	 * @param zos
	 * @param fileName
	 * @throws Exception
	 */
	private static void addZipFile(File file, ZipOutputStream zos,
			String fileName) throws Exception {
		int l;

		byte[] b = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);

		ZipEntry entry = new ZipEntry(fileName);

		zos.putNextEntry(entry);

		while ((l = fis.read(b)) != -1) {
			zos.write(b, 0, l);
		}

		entry = null;
		fis.close();
		b = null;

	}
}
