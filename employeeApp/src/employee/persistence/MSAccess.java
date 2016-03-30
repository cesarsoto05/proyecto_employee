
package employee.persistence;

import employee.logic.Empleado;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *MS Access database
 * @author Cesar
 */
public class MSAccess {
    
    private Connection connection; /** database conection */
    private Statement statement; /** SQL statement*/
    private ResultSet resultset; /**Result set */
    
    private final String dbFilename = "E:\\Construccion de Software\\proyecto_employee\\employeeApp\\src\\employee\\persistence\\EmployeeDB.accdb";

    /**
     * Default constructor
     */
    public MSAccess() {
        if(this.connect()){ 
        }
    }

    /**
     * Get database connection
     * @return database connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * set Database connection
     * @param connection database connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * get statement
     * @return statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * set statement
     * @param statement statement
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    /**
     * get result set
     * @return result set
     */
    public ResultSet getResultset() {
        return resultset;
    }

    /**
     * set result set
     * @param resultset result set
     */
    public void setResultset(ResultSet resultset) {
        this.resultset = resultset;
    }
    
    /**
     * try to connect to DB
     * @return true = connected, false = not connected
     */
    public boolean connect(){
        boolean connected = false;
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            this.setConnection(DriverManager.getConnection(
            "jdbc:ucanaccess://" + this.dbFilename));
            connected = true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MSAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connected;
    }
    
    /**
     * Selec employees from DB
     * @return Employee list
     */
    public List<Empleado> selectEmployee(){
        
        List<Empleado> employeeList = new ArrayList<>();
        
        try {
            this.setStatement(this.getConnection().createStatement());
            String query = "SELECT * FROM employee";
            this.getStatement().execute(query);
            this.setResultset(this.getStatement().getResultSet());
            
            if(this.getResultset() != null){
                while(this.getResultset().next()){
                    Empleado employee = new Empleado();
                    
                    employee.setId(this.getResultset().getLong("id"));
                    employee.setNombre(this.getResultset().getString("firstname"));
                    employee.setApellido(this.getResultset().getString("lastname"));
                    employee.setFoto(this.getResultset().getString("photo"));
                    employee.setGenero(this.getResultset().getInt("gender"));
                    employee.setFechaNacimiento(this.getResultset().getDate("bornDate"));
                    employee.setFechaIngreso(this.getResultset().getDate("hiredDate"));
                    employee.setSalario(this.getResultset().getInt("salary"));
                    
                    employeeList.add(employee);
                }
            }
            
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(MSAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return employeeList;
    }
    /**
     * query data
     * @param query query
     * @return true = successful operation
     */
    public boolean execute(String query){
        boolean result= false;
        
        try {
            this.setStatement(this.getConnection().createStatement());
            this.getStatement().execute(query);
            this.setResultset(this.getStatement().getResultSet());
            result= true;
        } catch (SQLException ex) {
            Logger.getLogger(MSAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
