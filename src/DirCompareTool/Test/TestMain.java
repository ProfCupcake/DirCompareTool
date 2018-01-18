/*
 * Driver class for testing the DirectoryCompare tool. 
 */
package DirCompareTool.Test;

import DirCompareTool.CompareResult;
import DirCompareTool.DirectoryCompare;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 *
 * @author Marshall
 */
public class TestMain
{
    static final String STR_TESTDIR1 = ".\\Test Directories\\Test Directory 1";
    static final String STR_TESTDIR2 = ".\\Test Directories\\Test Directory 2 (identical to One)";
    static final String STR_TESTDIR3 = ".\\Test Directories\\Test Directory 3 (2 files missing)";
    static final String STR_TESTDIR4 = ".\\Test Directories\\Test Directory 4 (same filenames, different files)";
    static final String STR_TESTDIR5 = ".\\Test Directories\\Test Directory 5 (same filenames, txt files same size but different content)";
    
    
    public static void main(String[] args) throws IOException
    {
        FileSystem fileSystem = FileSystems.getDefault();
        
        Path testDir1 = fileSystem.getPath(STR_TESTDIR1);
        Path testDir2 = fileSystem.getPath(STR_TESTDIR2);
        Path testDir3 = fileSystem.getPath(STR_TESTDIR3);
        Path testDir4 = fileSystem.getPath(STR_TESTDIR4);
        Path testDir5 = fileSystem.getPath(STR_TESTDIR5);
        
        System.out.println("TEST 1: Testing a directory against itself (should return 100% match)...");
        doTest(testDir1, testDir1);
        
        System.out.println("TEST 2: Testing 2 identical directories");
        doTest(testDir1, testDir2);
        
        System.out.println("TEST 3: second has files missing");
        doTest(testDir1, testDir3);
        
        System.out.println("TEST 4: second has same files but with different content, compare level 0");
        doTest(testDir1, testDir4);
        
        System.out.println("TEST 5: second has same files but with different content, compare level 1");
        doTest(testDir1, testDir4, 1);
        
        System.out.println("TEST 5: second has same file sizes but content differs, compare level 1");
        doTest(testDir1, testDir5, 1);
        
        System.out.println("TEST 5: second has same file sizes but content differs, compare level 2");
        doTest(testDir1, testDir5, 2);
    }
    
    public static void doTest(Path dir1, Path dir2) throws IOException
    {
        doTest(dir1, dir2, 0);
    }
    
    public static void doTest(Path dir1, Path dir2, int compareLevel) throws IOException
    {
        CompareResult result = DirectoryCompare.compare(dir1, dir2, compareLevel);
        System.out.println("Result: "+(result.getAmountMatched()*100)+"% match");
        System.out.println("");
        System.out.println("");
    }
}
