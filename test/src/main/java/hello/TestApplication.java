package hello;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by ling on 17/8/4.
 */
public class TestApplication {

    public static String decodeMaibiaoQrId(String qrScanId) {
        String prefix = null, encodeId = null;
        final int hyphenIndex = qrScanId.lastIndexOf('-');
        final int idIndex = qrScanId.indexOf("&");
        if (hyphenIndex != -1) {
            prefix = qrScanId.substring(0, hyphenIndex + 1).toUpperCase();
            encodeId = qrScanId.substring(hyphenIndex + 1,idIndex).toLowerCase();
        }
        if (prefix == null || encodeId.equals("") || encodeId.length() % 2 != 0) {
            return null;
        }

        final String key = "18612521";
        final List<Integer> secret = Stream.of(key.split("")).map(Integer::parseInt).collect(Collectors.toList());
        final int originLen = encodeId.length();
        final List<Character> encodeChars = new ArrayList<>();
        final StringBuilder originChars = new StringBuilder();
        for (int i = 0; i < originLen; i = i + 2) {
            final String encodeHexTwoChar = encodeId.substring(i, i + 2);
            final int encodeHexInt = Integer.parseInt(encodeHexTwoChar, 16);
            encodeChars.add((char) encodeHexInt);
        }
        for (int i = 0; i < encodeChars.size(); i++) {
            originChars.append((char) (encodeChars.get(i) ^ (secret.get(i % key.length()))));
        }
        return prefix + getMd5(originChars.toString()).toUpperCase();
    }

    /**
     * md5 加密, 可选结果字符串长度, 默认 md5 是 32位, 这里如果 bitLength == 16, 则截取 32 位中间 16位返回.
     */
    public static String getMd5(String origin, Integer bitLength) {
        final String md5Str = getMd5(origin);
        if (bitLength != null && bitLength.equals(16)) {
            return md5Str.substring(8, bitLength + 8);
        } else {
            return md5Str;
        }
    }

    /**
     * md5 加密.
     */
    public static String getMd5(String origin) {
        if (origin == null) {
            throw new IllegalArgumentException("digest origin string must not be null!");
        }
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final StringBuilder sb = new StringBuilder();
            for (byte b : digest.digest(origin.getBytes())) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("digest instance get error!");
        }
    }


    public static void main(String... args) {
        Num a1 = new Num(2);
        Num a2 = new Num(1);
        Num a3 = new Num(5);
        Num a4 = new Num(3);
        List<Num> a = Arrays.asList(a1,a2,a3,a4);
        Optional<Num> first = a.stream().sorted(Comparator.comparingInt(Num::getA).reversed()).findFirst();
        System.out.println(first.get().getA());
    }

    public static class Num {
        private int a;

        public Num(int a) {
            this.a = a;
        }

        public int getA() {
            return a;
        }
    }




}
