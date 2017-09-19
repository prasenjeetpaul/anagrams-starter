/* Copyright 2016 Google Inc.
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

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary
{

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private static final ArrayList<String> wordList = new ArrayList<>();
    private static HashSet<String> dictionary = new HashSet<>();
    private static HashMap<String, ArrayList<String>> hashMap = new HashMap<>();


    public AnagramDictionary(Reader reader) throws IOException
    {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null)
        {
            String word = line.trim();
            dictionary.add(word);
            wordList.add(word);

            String sortedWord = getSortedString(word);

            if(hashMap.containsKey(sortedWord))
            {
                hashMap.get(sortedWord).add(word);

            }
            else
            {
                ArrayList list = new ArrayList();
                list.add(word);
                hashMap.put(sortedWord,list);
            }

        }
    }

    public boolean isGoodWord(String word, String base)
    {
        return getSortedString(word).equals(getSortedString(base)) && !word.contains(base) && dictionary.contains(word);

        //List<String> oneMoreWord = getAnagramsWithOneMoreLetter(word);
    }

    public List<String> getAnagrams(String targetWord)
    {
        return hashMap.get(getSortedString(targetWord));
    }

    public List<String> getAnagramsWithOneMoreLetter(String word)
    {
        ArrayList<String> result = new ArrayList<>();
        for(char i='a' ; i<='z'  ;i++)
        {
            result.addAll(getAnagrams(word+i));
        }
        return result;
    }

    public String pickGoodStarterWord()
    {
        while(true)
        {
            String word = wordList.get(Math.abs(random.nextInt(wordList.size())));
            if(hashMap.get(getSortedString(word)).size() > MIN_NUM_ANAGRAMS && word.length()<= MAX_WORD_LENGTH)
            {
                return word;
            }
        }
    }

    public String getSortedString(String word)
    {
       char string[] = word.toCharArray();
        Arrays.sort(string);
        return new String(string);
    }
}
