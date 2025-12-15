package KPODZ3.AnalisysService.Service;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

@Service
public class TextService {

    public String extractTextFromPdf(byte[] fileBytes) throws Exception {
        try (PDDocument document = Loader.loadPDF(fileBytes)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public String extractTextFromDocx(byte[] fileBytes) throws Exception {
        try (ByteArrayInputStream is = new ByteArrayInputStream(fileBytes);
                XWPFDocument doc = new XWPFDocument(is);
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {

            return extractor.getText();
        }
    }

    public String extractTextFromTxt(byte[] fileBytes) {
        try {
            return new String(fileBytes, "Windows-1251");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String normalize(String text) {
        return text
                .toLowerCase()
                .replaceAll("[^a-zа-я0-9\\s]", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public List<String> getShingles(String text, int size) {
        String[] words = text.split(" ");
        List<String> shingles = new ArrayList<>();

        for (int i = 0; i <= words.length - size; i++) {
            String shingle = String.join(" ",
                    Arrays.copyOfRange(words, i, i + size));
            shingles.add(shingle);
        }
        return shingles;
    }

    public Set<String> hashShingles(List<String> shingles) {
        Set<String> hashes = new HashSet<>();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            for (String shingle : shingles) {
                byte[] hash = digest.digest(shingle.getBytes());
                hashes.add(Base64.getEncoder().encodeToString(hash));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return hashes;
    }

    public double similarity(Set<String> set1, Set<String> set2) {
        if (set1.isEmpty() && set2.isEmpty())
            return 1.0;

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

    public boolean isPlagiate(List<Set<String>> existingFingerprints, Set<String> fingerprint) {
        for (Set<String> oldFp : existingFingerprints) {
            double sim = similarity(fingerprint, oldFp);
            if (sim > 0.4) {
                return true;
            }
        }

        return false;
    }
}
