/*
 * Directory comparison class - given two directories, will compare them to see how similar they are. 
 */
package DirCompareTool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import DirCompareTool.Test.Debug;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marshall
 */
public class DirectoryCompare
{

    /**
     *
     * @param dir1
     * @param dir2
     * @param compareLevel
     * @return
     * @throws java.io.IOException
     */
    public static CompareResult compare(Path dir1, Path dir2, int compareLevel) throws IOException
    {

        Debug.debugMessage("Beginning comparison between '" + dir1.toString() + "' and '" + dir2.toString() + "'...");

        Stream<Path> dir1walk = Files.walk(dir1);

        /*
         * Notes for expansion:-
         *  - Add option for reverse double-checking (i.e. walk dir2 and check against as well)
         *  - Add options deeper checks for matching files (e.g. checking file size, hashing files)
         *  - Option to ignore directories themselves?
         */
        CompareResult result = new CompareResult();

        dir1walk.forEach((Path t) ->
        {
            Path relativePath = dir1.relativize(t);
            Debug.debugMessage("Current file relative path: " + relativePath.toString());

            Path i = dir2.resolve(relativePath);

            if (dir1.resolve(relativePath).equals(dir1))
            {
                Debug.debugMessage("Ignoring root folder");
            } else
            {
                if (i.toFile().exists())
                {
                    if (compareLevel < 1 || t.toFile().isDirectory() || fileSize(t) == fileSize(i))
                    {
                        if (compareLevel < 2 || t.toFile().isDirectory() || digest(t) == digest(i))
                        {
                            Debug.debugMessage("Match found.");
                            result.addMatch(t);
                        } else
                        {
                            Debug.debugMessage("Files do not match - hash differs");
                            result.addDiscrepancy(t);
                        }
                    } else
                    {

                        Debug.debugMessage("Files do not match - different size");
                        result.addDiscrepancy(t);
                    }
                } else
                {
                    Debug.debugMessage("File missing");
                    result.addMissing(t);
                }
            }
        });

        return result;
    }

    public static CompareResult compare(Path dir1, Path dir2) throws IOException
    {
        return compare(dir1, dir2, 0);
    }

    private static byte[] digest(Path p)
    {
        MessageDigest md = null;
        DigestInputStream is;
        try
        {
            md = MessageDigest.getInstance("MD5");
            is = new DigestInputStream(Files.newInputStream(p), md);
            while (is.available() > 0)
            {
                is.read();
            }
        } catch (NoSuchAlgorithmException | IOException ex)
        {
            Logger.getLogger(DirectoryCompare.class.getName()).log(Level.SEVERE, null, ex);
        }
        return md.digest();
    }

    private static long fileSize(Path p)
    {
        return p.toFile().length();
    }
}
