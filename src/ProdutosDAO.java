/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        conectaDAO dao = new conectaDAO();
        try {
            conn = dao.connectDB();
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, produto.getNome());
            statement.setInt(2, produto.getValor());
            statement.setString(3, produto.getStatus());
            int linhasAfetadas = statement.executeUpdate();
            System.out.println("Linhas afetadas: " + linhasAfetadas);
            JOptionPane.showMessageDialog(null,"Dados cadastrados com sucesso.");
            dao.getConn().close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro de cadastro: "+e);  
        } 
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        conectaDAO dao = new conectaDAO();
        try {
            conn = dao.connectDB();
            prep = conn.prepareStatement("SELECT * FROM produtos");
            resultset = prep.executeQuery();
            
            while(resultset.next()){ 
                ProdutosDTO v = new ProdutosDTO();
                v.setId(resultset.getInt("Id"));
                v.setNome(resultset.getString("Nome"));
                v.setValor(resultset.getInt("Valor"));
                v.setStatus(resultset.getString("Status"));
                
                listagem.add(v);
            }
            dao.getConn().close();
            return listagem;
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public void venderProduto (int id){
        conectaDAO dao = new conectaDAO();
        try {
            conn = dao.connectDB();
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            int linhasAfetadas = statement.executeUpdate();
            System.out.println("Linhas afetadas: " + linhasAfetadas);
            JOptionPane.showMessageDialog(null,"Dados atualizados com sucesso.");
            dao.getConn().close();
        } catch (SQLException e) {
            System.out.println("Erro de atualização: "+e);  
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos (){
        conectaDAO dao = new conectaDAO();
        try {
            conn = dao.connectDB();
            prep = conn.prepareStatement("SELECT * FROM produtos WHERE status = 'Vendido'");
            resultset = prep.executeQuery();
            
            while(resultset.next()){ 
                ProdutosDTO v = new ProdutosDTO();
                v.setId(resultset.getInt("Id"));
                v.setNome(resultset.getString("Nome"));
                v.setValor(resultset.getInt("Valor"));
                v.setStatus(resultset.getString("Status"));
                
                listagem.add(v);
            }
            
            dao.getConn().close();
            return listagem;
            
        } catch (SQLException ex) {
            return null;
        }
    }
        
}

