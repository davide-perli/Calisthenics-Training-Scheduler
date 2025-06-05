import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Static_exercise extends Exercise {
    private Service_class x;
    private DatabaseService db = DatabaseService.getInstance();

    public Static_exercise() {
        x = new Service_class();
        loadFromDatabase();
        this.exercises = new ArrayList<>(x.exercises); // copy in list for main
    }

    @Override
    public List<String> getAllExercisesFromDB() {
        String sql = "SELECT name FROM static_exercise";
        try {
            return db.executeQuery(sql, null, rs -> {
                List<String> list = new ArrayList<>();
                try {
                    while (rs.next()) {
                        list.add(rs.getString("name"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return list;
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void addExerciseToDB(String name, String skillLevel) {
        String sql = "INSERT INTO static_exercise (name, skill_level) VALUES (?, ?)";
        try {
            db.executeUpdate(sql, pstmt -> {
                try {
                    pstmt.setString(1, name);
                    pstmt.setString(2, skillLevel);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateExerciseName(int id, String newName) {
        String sql = "UPDATE static_exercise SET name = ? WHERE id = ?";
        try {
            db.executeUpdate(sql, pstmt -> {
                try {
                    pstmt.setString(1, newName);
                    pstmt.setInt(2, id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteExercise(int id) {
        String sql = "DELETE FROM static_exercise WHERE id = ?";
        try {
            db.executeUpdate(sql, pstmt -> {
                try {
                    pstmt.setInt(1, id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFromDatabase() {
        String sql = "SELECT name, skill_level FROM static_exercise";
        try {
            db.executeQuery(sql, null, rs -> {
                try {
                    while (rs.next()) {
                        String name  = rs.getString("name");
                        String skill = rs.getString("skill_level");
                        x.addInput(name);
                        x.putInput(name, skill);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return null;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getExercise(int index) {
        return exercises.get(index);
    }

    @Override
    public HashMap<String, String> getExerciseRanked() {
        return x.getExerciseRanked();
    }
}
