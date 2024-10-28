public class CourseScheduler {
    public static int maxNonOverlappingCourses(int[][] courses) {
        sortCourses(courses);

        int result = 0;
        int lastEndTime = -1;

        for (int[] course : courses) {
            int startTime = course[0];
            int endTime = course[1];

            if (startTime >= lastEndTime) {
                result++;
                lastEndTime = endTime;
            }
        }

        return result;
    }

    public static void sortCourses(int[][] courses) {
        int n = courses.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (courses[j][1] < courses[minIndex][1]) {
                    minIndex = j;
                }
            }

            int[] temp = courses[i];
            courses[i] = courses[minIndex];
            courses[minIndex] = temp;
        }
    }
}
