/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisal.aula.dal;
import java.sql.*;

/**
 *
 * @author mtheu
 */
public class ModuloConexao {
    // modulo responsavel por estabelecer a conexão com o banco de dados
    public static Connection conector(){
        java.sql.Connection conexao = null;
        // chamar o driver
        String driver = "com.mysql.jdbc.Driver";
        
        // informações referentes ao banco de dados (XAMP/ LOCALHOST)
        String url = "jdbc:mysql://localhost:3306/projetointegrado";
        String user = "root";
        String password = "";
        
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            // a linha abaixo serve para exatamente onde está o erro
            //System.out.println(e);
            return null;
        }
            
    }
}
