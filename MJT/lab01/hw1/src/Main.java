public class Main {
    public static void main(String[] args) {
        System.out.println(CourseScheduler.maxNonOverlappingCourses(new int[][]{{9, 11}, {10, 12}, {11, 13}, {15, 16}}));
        System.out.println(CourseScheduler.maxNonOverlappingCourses(new int[][]{{19, 22}, {17, 19}, {9, 12}, {9, 11}, {15, 17}, {15, 17}}));
        System.out.println(CourseScheduler.maxNonOverlappingCourses(new int[][]{{19, 22}}));
        System.out.println(CourseScheduler.maxNonOverlappingCourses(new int[][]{{13, 15}, {13, 17}, {11, 17}}));
    }
}