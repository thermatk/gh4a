/*
 * Copyright 2011 Azwan Adli Abdullah
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gh4a.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class StringUtils.
 */
public class StringUtils {

    /** The Constant DATE_FORMAT. */
    private static final String DATE_FORMAT = "MMM dd, yyyy";

    /** The Constant MEGABYTE. */
    private static final long MEGABYTE = 1024L;

    /**
     * Checks if is blank.
     * 
     * @param val the val
     * @return true, if is blank
     */
    public static boolean isBlank(String val) {
        return val == null || (val != null && val.trim().equals(""));
    }

    /**
     * Format long text dot.
     * 
     * @param text the text
     * @return the string
     */
    public static String formatLongTextDot(String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        else {
            int index = text.indexOf(".");
            if (index != -1) {
                return text.substring(0, index);
            }
            else {
                return StringUtils.formatLongText(text);
            }
        }
    }

    /**
     * Format long text.
     * 
     * @param text the text
     * @return the string
     */
    public static String formatLongText(String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        else {
            if (text.length() <= 20) {
                return text;
            }
            else if (text.length() > 20) {
                int newLineIndex = text.indexOf("\n");
                if (newLineIndex != -1) {
                    return text.substring(0, newLineIndex);
                }
                else {
                    return text.substring(0, text.length() > 20 ? 20 : text.length()) + "...";
                }
            }
            else {
                return text.substring(0, text.length() > 20 ? 20 : text.length()) + "...";
            }
        }
    }

    /**
     * Format long text.
     * 
     * @param text the text
     * @param length the length
     * @return the string
     */
    public static String formatLongText(String text, int length) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        else {
            if (text.length() <= length) {
                return text;
            }
            else {
                return text.substring(0, length);
            }
        }
    }

    /**
     * Do teaser.
     * 
     * @param text the text
     * @return the string
     */
    public static String doTeaser(String text) {
        if (!StringUtils.isBlank(text)) {
            int indexNewLine = text.indexOf("\n");
            int indexDot = text.indexOf(".");

            if (indexDot != -1 && indexNewLine != -1) {
                if (indexDot > indexNewLine) {
                    text = text.substring(0, indexNewLine);
                }
                else {
                    text = text.substring(0, indexDot + 1);
                }
            }
            else if (indexDot != -1) {
                text = text.substring(0, indexDot + 1);
            }
            else if (indexNewLine != -1) {
                text = text.substring(0, indexNewLine);
            }

            return text;
        }
        return "";
    }

    /**
     * Repeat.
     * 
     * @param val the val
     * @param count the count
     * @return the string
     */
    public static String repeat(String val, int count) {
        for (int i = 0; i < count; i++) {
            val += val;
        }
        return val;
    }

    /**
     * Kbytes to meg.
     * 
     * @param kbytes the kbytes
     * @return the float
     */
    public static float kbytesToMeg(long kbytes) {
        return kbytes / MEGABYTE;
    }

    /**
     * Md5 hex.
     * 
     * @param s the s
     * @return the string
     */
    public static String md5Hex(String s) {
        String result = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(s.getBytes());
            result = toHex(digest);
        }
        catch (NoSuchAlgorithmException e) {
            // this won't happen, we know Java has MD5!
        }
        return result;
    }

    /**
     * To hex.
     * 
     * @param a the a
     * @return the string
     */
    public static String toHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (int i = 0; i < a.length; i++) {
            sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(a[i] & 0x0f, 16));
        }
        return sb.toString();
    }

    /**
     * If null default to.
     * 
     * @param ori the ori
     * @param defaultTo the default to
     * @return the string
     */
    public static String ifNullDefaultTo(String ori, String defaultTo) {
        if (!StringUtils.isBlank(ori)) {
            return ori;
        }
        else {
            if (!StringUtils.isBlank(defaultTo)) {
                return defaultTo;
            }
            else {
                return "";
            }
        }
    }

    /**
     * Format name.
     * 
     * @param userLogin the user login
     * @param name the name
     * @return the string
     */
    public static String formatName(String userLogin, String name) {
        String formattedName;

        if (!StringUtils.isBlank(name)) {
            formattedName = userLogin + " - " + name;
        }
        else {
            formattedName = userLogin;
        }
        return formattedName;
    }

    /**
     * Convert stream to string.
     *
     * @param is the is
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String convertStreamToString(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the Reader.read(char[]
         * buffer) method. We iterate until the Reader return -1 which means
         * there's no more data to read. We use the StringWriter class to
         * produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            }
            finally {
                is.close();
            }
            return writer.toString();
        }
        else {
            return "";
        }
    }

    /**
     * Format date.
     *
     * @param date the date
     * @return the string
     */
    public static String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.DATE_FORMAT);
            return sdf.format(date);
        }
        else {
            return "";
        }
    }

    /**
     * Gets the file extension.
     *
     * @param filename the filename
     * @return the file extension
     */
    public static String getFileExtension(String filename) {
        int mid = filename.lastIndexOf(".");
        if (mid != -1) {
            return filename.substring(mid + 1, filename.length());
        }
        else {
            return "";
        }
    }
    
    public static String toHumanReadbleFormat(long size) {
        NumberFormat nf = new DecimalFormat("0.#");
        String s;
        float f;
        if (size / (1024 * 1024) > 0) {
            f = size;
            s = nf.format(f / (1024 * 1024)) + " GB";
        }
        else if (size / (1024) > 0) {
            f = size;
            s = nf.format(f / (1024)) + " MB";
        }
        else {
            f = size;
            s = nf.format(f) + " bytes";
        }
        return s;
    }
}