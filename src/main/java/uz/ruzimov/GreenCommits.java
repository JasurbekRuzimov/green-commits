package uz.ruzimov;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GreenCommits {
  public static void main(String[] args) throws IOException, InterruptedException {
    Random random = new Random();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LocalDate startDate = LocalDate.of(2024, 3, 31);
    LocalDate endDate = LocalDate.now();

    while (!startDate.isAfter(endDate)) {
      int commitCount = 5 + random.nextInt(11);
      for (int i = 0; i < commitCount; i++) {

        String content = "Commit made on " + startDate.format(formatter) + " - #" + (i + 1);
        writeToFile("activity.txt", content);


        String commitDate = startDate.format(formatter) + "T12:" + String.format("%02d", random.nextInt(59)) + ":00";

        String gitCommand = String.format(
            "GIT_AUTHOR_DATE=\"%s\" GIT_COMMITTER_DATE=\"%s\" git commit -a -m \"Commit %d on %s\"",
            commitDate, commitDate, (i + 1), startDate.format(formatter)
        );

        runCommand(gitCommand);
      }

      System.out.println("✅ " + startDate + " -> " + commitCount + " commits created.");
      startDate = startDate.plusDays(1);
    }

    System.out.println("\n All commits created successfully!");
    System.out.println("Now run: git push -u origin main");
  }

  private static void writeToFile(String filename, String content) throws IOException {
    try (FileWriter fw = new FileWriter(filename, true)) {
      fw.write(content + "\n");
    }
  }

  private static void runCommand(String command) throws IOException, InterruptedException {
    ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
    pb.inheritIO();
    Process process = pb.start();
    process.waitFor();
  }
}
