/**
 * Copyright (c) 2011-2013, kidzhou 鍛ㄧ (zhouleib1412@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.hzhou.ext.jfinal.kit;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.common.collect.Lists;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;

public class ClassSearcher {

    protected static final Logger LOG = Logger.getLogger(ClassSearcher.class);

    @SuppressWarnings("unchecked")
    private static <T> List<Class<? extends T>> extraction(Class<T> clazz, List<String> classFileList) {
        List<Class<? extends T>> classList = Lists.newArrayList();
        for (String classFile : classFileList) {
            Class<?> classInFile = Reflect.on(classFile).get();
            if (clazz.isAssignableFrom(classInFile) && clazz != classInFile) {
                classList.add((Class<? extends T>) classInFile);
            }
        }

        return classList;
    }

    public static ClassSearcher of(Class target) {
        return new ClassSearcher(target);
    }

    /**
     * 閫掑綊鏌ユ壘鏂囦欢
     * 
     * @param baseDirName
     *            鏌ユ壘鐨勬枃浠跺す璺緞
     * @param targetFileName
     *            闇�瑕佹煡鎵剧殑鏂囦欢鍚�
     */
    private static List<String> findFiles(String baseDirName, String targetFileName) {
        /**
         * 绠楁硶绠�杩帮細 浠庢煇涓粰瀹氱殑闇�鏌ユ壘鐨勬枃浠跺す鍑哄彂锛屾悳绱㈣鏂囦欢澶圭殑鎵�鏈夊瓙鏂囦欢澶瑰強鏂囦欢锛� 鑻ヤ负鏂囦欢锛屽垯杩涜鍖归厤锛屽尮閰嶆垚鍔熷垯鍔犲叆缁撴灉闆嗭紝鑻ヤ负瀛愭枃浠跺す锛屽垯杩涢槦鍒椼�� 闃熷垪涓嶇┖锛岄噸澶嶄笂杩版搷浣滐紝闃熷垪涓虹┖锛岀▼搴忕粨鏉燂紝杩斿洖缁撴灉銆�
         */
        List<String> classFiles = Lists.newArrayList();
        String tempName = null;
        // 鍒ゆ柇鐩綍鏄惁瀛樺湪
        File baseDir = new File(baseDirName);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            LOG.error("search error锛�" + baseDirName + "is not a dir锛�");
        } else {
            String[] filelist = baseDir.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(baseDirName + File.separator + filelist[i]);
                if (readfile.isDirectory()) {
                    classFiles.addAll(findFiles(baseDirName + File.separator + filelist[i], targetFileName));
                } else {
                    tempName = readfile.getName();
                    if (ClassSearcher.wildcardMatch(targetFileName, tempName)) {
                        String classname;
                        String tem = readfile.getAbsoluteFile().toString().replaceAll("\\\\", "/");
                        classname = tem.substring(tem.indexOf("/classes") + "/classes".length() + 1,
                                tem.indexOf(".class"));
                        classFiles.add(classname.replaceAll("/", "."));
                    }
                }
            }
        }
        return classFiles;
    }

    /**
     * 閫氶厤绗﹀尮閰�
     * 
     * @param pattern
     *            閫氶厤绗︽ā寮�
     * @param str
     *            寰呭尮閰嶇殑瀛楃涓� <a href="http://my.oschina.net/u/556800" target="_blank" rel="nofollow">@return</a>
     *            鍖归厤鎴愬姛鍒欒繑鍥瀟rue锛屽惁鍒欒繑鍥瀎alse
     */
    private static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                // 閫氶厤绗︽槦鍙�*琛ㄧず鍙互鍖归厤浠绘剰澶氫釜瀛楃
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1), str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                // 閫氶厤绗﹂棶鍙�?琛ㄧず鍖归厤浠绘剰涓�涓瓧绗�
                strIndex++;
                if (strIndex > strLength) {
                    // 琛ㄧずstr涓凡缁忔病鏈夊瓧绗﹀尮閰�?浜嗐��
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return strIndex == strLength;
    }

    private String classpath = PathKit.getRootClassPath();

    private boolean includeAllJarsInLib = false;

    private List<String> includeJars = Lists.newArrayList();

    private String libDir = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + "lib";

    private Class target;

    public ClassSearcher(Class target) {
        this.target = target;
    }

    public ClassSearcher injars(List<String> jars) {
        if (jars != null) {
            includeJars.addAll(jars);
        }
        return this;
    }

    public ClassSearcher inJars(String... jars) {
        if (jars != null) {
            for (String jar : jars) {
                includeJars.add(jar);
            }
        }
        return this;
    }

    public ClassSearcher classpath(String classpath) {
        this.classpath = classpath;
        return this;
    }

    public <T> List<Class<? extends T>> search() {
        List<String> classFileList = findFiles(classpath, "*.class");
        classFileList.addAll(findjarFiles(libDir, includeJars));
        return extraction(target, classFileList);
    }

    /**
     * 鏌ユ壘jar鍖呬腑鐨刢lass
     * 
     * @param baseDirName
     *            jar璺緞
     * @param includeJars
     * @param jarFileURL
     *            jar鏂囦欢鍦板潃 <a href="http://my.oschina.net/u/556800" target="_blank" rel="nofollow">@return</a>
     */
    private List<String> findjarFiles(String baseDirName, final List<String> includeJars) {
        List<String> classFiles = Lists.newArrayList();
        try {
            // 鍒ゆ柇鐩綍鏄惁瀛樺湪
            File baseDir = new File(baseDirName);
            if (!baseDir.exists() || !baseDir.isDirectory()) {
                LOG.error("file serach error锛�" + baseDirName + " is not a dir锛�");
            } else {
                String[] filelist = baseDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return includeAllJarsInLib || includeJars.contains(name);
                    }
                });
                for (int i = 0; i < filelist.length; i++) {
                    JarFile localJarFile = new JarFile(new File(baseDirName + File.separator + filelist[i]));
                    Enumeration<JarEntry> entries = localJarFile.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();
                        String entryName = jarEntry.getName();
                        if (!jarEntry.isDirectory() && entryName.endsWith(".class")) {
                            String className = entryName.replaceAll("/", ".").substring(0, entryName.length() - 6);
                            classFiles.add(className);
                        }
                    }
                    localJarFile.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return classFiles;

    }

    public ClassSearcher includeAllJarsInLib(boolean includeAllJarsInLib) {
        this.includeAllJarsInLib = includeAllJarsInLib;
        return this;
    }

    public ClassSearcher libDir(String libDir) {
        this.libDir = libDir;
        return this;
    }

}
