package com.iisi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WordCompnetImpl implements WordCompnet {

	private static transient Logger LOG = LoggerFactory
			.getLogger(WordCompnetImpl.class);

	public void copyFromTemplate(String templatePath, Map<String, String> map,
			String outPath) {

		POIFSFileSystem fs = null;
		try {
			LOG.info("templatePath:{}", templatePath);
			fs = new POIFSFileSystem(new FileInputStream(templatePath));
			HWPFDocument doc = new HWPFDocument(fs);// 取得範本

			for (Entry<String, String> entry : map.entrySet()) {
				doc = replaceText(doc, entry.getKey(), entry.getValue());
			}

			saveWord(outPath, doc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOG.error("e:{}", e.getMessage(), e);
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("e:{}", e.getMessage(), e);
		}

	}

	private HWPFDocument replaceText(HWPFDocument doc, String findText,
			String replaceText) {
		Range r1 = doc.getRange();

		for (int i = 0; i < r1.numSections(); ++i) {
			LOG.info("select :{}", i);
			Section s = r1.getSection(i);
			for (int x = 0; x < s.numParagraphs(); x++) {
				LOG.info("Paragraph :{}", x);
				Paragraph p = s.getParagraph(x);

				for (int z = 0; z < p.numCharacterRuns(); z++) {
					CharacterRun run = p.getCharacterRun(z);
					String text = run.text();
					LOG.info("word{} :{}", z, text);
					if (text.contains(findText)) {
						run.replaceText(findText, replaceText);
					}
				}
			}
		}
		return doc;
	}

	private void saveWord(String filePath, HWPFDocument doc)
			throws FileNotFoundException, IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			doc.write(out);
		} finally {
			out.close();
		}
	}

	public void copyFromTemplate(String templatePath,
			List<Map<String, String>> mapList, List<String> outPath) {
		int index = 0;
		for (Map<String, String> map : mapList) {
			this.copyFromTemplate(templatePath, map, outPath.get(index));
			index++;
		}

	}

}
