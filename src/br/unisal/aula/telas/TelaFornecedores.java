/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisal.aula.telas;
import java.sql.*;
import br.unisal.aula.dal.ModuloConexao;
import javax.swing.JOptionPane;
// a linha abaixo importa recursos da biblioteca rs2xml.jar
// serve para fazer a tabela avançada de pesquisa
import net.proteanit.sql.DbUtils;
/**
 *
 * @author Matheus Silva
 */
public class TelaFornecedores extends javax.swing.JInternalFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaFornecedores
     */
    public TelaFornecedores() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    //Método para adicionar fornecedores
    public void adicionar() {
        String sql = "insert into tbfornecedores(nome, cnpj, IE, endereco, cidade, telefone, pagamento, email)  values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtForNome.getText());
            pst.setString(2, txtForCNPJ.getText());
            pst.setString(3, txtForInscrição.getText());
            pst.setString(4, txtForEndereco.getText());
            pst.setString(5, txtForCidade.getText());
            pst.setString(6, txtForFone.getText());
            pst.setString(7, cboForPagamento.getSelectedItem().toString());
            pst.setString(8, txtForEmail.getText());

            // validação dos campos obrigatorios
            if ((txtForNome.getText().isEmpty()) || (txtForCNPJ.getText().isEmpty()) || (txtForInscrição.getText().isEmpty()) 
                    || (txtForEndereco.getText().isEmpty()) || (txtForCidade.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios!!");

            } else {
                // a linha abaixo atualiza a tabela fornecedores com os dados adicionados no formulario
                // a estrutura abaixo é usada para confirmar os dados adicionados na tabela
                int adicionado = pst.executeUpdate();
                /* Caso o 'adicionado' for (1), o fornecedor foi adicionado com sucesso, 
             * caso for 0 ele não terá sido adicionado por algum erro. 
                 */
                // a linha abaixo serve para entendimento da lógica
                // System.out.println(adicionado); // Se for adicionado sem problemas, estará mostrando o número 1. 
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Fornecedor adicionado com sucesso!");
                    // os códigos abaixo estarão apagando os campos do formulario
                    txtForNome.setText(null);
                    txtForCNPJ.setText(null);
                    txtForInscrição.setText(null);
                    txtForEndereco.setText(null);
                    txtForCidade.setText(null);
                    txtForFone.setText(null);
                    txtForEmail.setText(null);
                } 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para pesquisar clientes pelo nome, com filtro!
    // Consulta Avançada *** 
    private void pesquisar_funcionarios() {
        String sql = "select * from tbfornecedores where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa (txtFunPesquisar) para o > "?" <
            // atenção ao % que é a continuação do comando SQL (falta a % para o código funcionar no MYSQL)
            pst.setString(1, txtForPesquisar.getText() + "%"); // + % para funcionar o comando sql
            rs = pst.executeQuery();
            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblFornecedores.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     // métodos para setar os campos do formulario com o couteudo da tabela
    public void setar_campos(){
        int setar = tblFornecedores.getSelectedRow();
        txtForID.setText(tblFornecedores.getModel().getValueAt(setar, 0).toString());
        txtForNome.setText(tblFornecedores.getModel().getValueAt(setar, 1).toString());
        txtForCNPJ.setText(tblFornecedores.getModel().getValueAt(setar, 2).toString());
        txtForInscrição.setText(tblFornecedores.getModel().getValueAt(setar, 3).toString());
        txtForEndereco.setText(tblFornecedores.getModel().getValueAt(setar, 4).toString());
        txtForCidade.setText(tblFornecedores.getModel().getValueAt(setar, 5).toString());
        txtForFone.setText(tblFornecedores.getModel().getValueAt(setar, 6).toString());
        cboForPagamento.setSelectedItem(tblFornecedores.getValueAt(setar, 7).toString());
        txtForEmail.setText(tblFornecedores.getModel().getValueAt(setar, 8).toString());
        
        // a linha abaixo desabilita o botão adicionar
        // assim não tem como adicionar um usuario já cadastrado
        btnAdicionar.setEnabled(false);
    }
    
    // método para alterar os funcionarios
    public void alterar() {
        String sql = "update tbfornecedores set nome = ?, cnpj = ?, IE = ?, endereco = ?, cidade = ?, telefone = ?, pagamento = ?, email = ?  where idforn = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtForNome.getText());
            pst.setString(2, txtForCNPJ.getText());
            pst.setString(3, txtForInscrição.getText());
            pst.setString(4, txtForEndereco.getText());
            pst.setString(5, txtForCidade.getText());
            pst.setString(6, txtForFone.getText());
            pst.setString(7, cboForPagamento.getSelectedItem().toString());
            pst.setString(8, txtForEmail.getText());
            pst.setString(9, txtForID.getText());
            
            // validação dos campos obrigatorios
            if ((txtForNome.getText().isEmpty()) || (txtForCNPJ.getText().isEmpty()) || (txtForInscrição.getText().isEmpty()) 
                    || (txtForEndereco.getText().isEmpty()) || (txtForCidade.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios!!");

            } else {
                // a linha abaixo atualiza a tabela fornecedores
                // a estrutura abaixo é usada para confirmar os dados adicionados na tabela
                int adicionado = pst.executeUpdate();
                /* Caso o 'adicionado' for (1), o fornecedor foi adicionado com sucesso, 
                 * caso for 0 ele não terá sido adicionado por algum erro. 
                 */
                // a linha abaixo serve para entendimento da lógica
                // System.out.println(adicionado); // Se for adicionado sem problemas, estará mostrando o número 1. 
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do fornecedor alterados com sucesso!!!");
                    // os códigos abaixo estarão apagando os campos do formulario
                    txtForID.setText(null);
                    txtForNome.setText(null);
                    txtForCNPJ.setText(null);
                    txtForInscrição.setText(null);
                    txtForEndereco.setText(null);
                    txtForCidade.setText(null);
                    txtForFone.setText(null);
                    txtForEmail.setText(null);
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método responsavel pela remoção de funcionarios
    public void remover(){
        // a estrutura abaixo confirma a remoção do usuario
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este fornecedor?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbfornecedor where idforn = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtForID.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0 ){
                    JOptionPane.showMessageDialog(null, "Fornecedor removido com sucesso!");
                    txtForID.setText(null);
                    txtForNome.setText(null);
                    txtForCNPJ.setText(null);
                    txtForInscrição.setText(null);
                    txtForEndereco.setText(null);
                    txtForCidade.setText(null);
                    txtForFone.setText(null);
                    txtForEmail.setText(null);
                    btnAdicionar.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtForPesquisar = new javax.swing.JTextField();
        txtForFone = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtForEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFornecedores = new javax.swing.JTable();
        btnAlterar = new javax.swing.JButton();
        txtForEndereco = new javax.swing.JTextField();
        txtForCNPJ = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnDeletar = new javax.swing.JButton();
        txtForCidade = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtForID = new javax.swing.JTextField();
        txtForInscrição = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cboForPagamento = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtForNome = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setMaximumSize(new java.awt.Dimension(702, 489));
        setMinimumSize(new java.awt.Dimension(702, 489));

        txtForPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtForPesquisarMouseClicked(evt);
            }
        });
        txtForPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtForPesquisarActionPerformed(evt);
            }
        });
        txtForPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtForPesquisarKeyReleased(evt);
            }
        });

        txtForFone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtForFoneKeyTyped(evt);
            }
        });

        jLabel3.setText("Telefone:");

        jLabel6.setText("Cidade*:");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/unisal/aula/icones/search.png"))); // NOI18N
        jLabel5.setToolTipText("Consultar");

        jLabel4.setText("E-mail:");

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projetointegrado/img/add.png"))); // NOI18N
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        tblFornecedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblFornecedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFornecedoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblFornecedores);

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projetointegrado/img/update.png"))); // NOI18N
        btnAlterar.setToolTipText("Editar");
        btnAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        txtForCNPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtForCNPJActionPerformed(evt);
            }
        });
        txtForCNPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtForCNPJKeyTyped(evt);
            }
        });

        jLabel1.setText("CNPJ*:");

        jLabel10.setText("ID Fornecedor:");

        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projetointegrado/img/delete.png"))); // NOI18N
        btnDeletar.setToolTipText("Deletar");
        btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        jLabel7.setText("* = Campos Obrigatórios");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Cadastro de Fornecedores");

        jLabel8.setText("I. Estadual*:");

        jLabel2.setText("Endereço*:");

        txtForID.setEnabled(false);

        txtForInscrição.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtForInscriçãoKeyTyped(evt);
            }
        });

        jLabel11.setText("Pagamento:");

        cboForPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Antecipado", "À Vista", "Faturado (30/60/120)" }));
        cboForPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboForPagamentoActionPerformed(evt);
            }
        });

        jLabel12.setText("Nome:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel12)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addGap(35, 35, 35)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtForFone, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtForCNPJ, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtForEndereco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cboForPagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jLabel6))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtForCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtForInscrição, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addComponent(txtForEmail))
                                .addComponent(txtForNome, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtForID, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel7)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtForPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtForPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtForID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtForNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtForCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtForInscrição, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtForCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtForEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtForFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel11)
                    .addComponent(cboForPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtForEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(btnDeletar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                .addGap(107, 107, 107))
        );

        setBounds(0, 0, 702, 489);
    }// </editor-fold>//GEN-END:initComponents

    private void txtForPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtForPesquisarMouseClicked
        // chamando o método para setar os campos
        setar_campos();
    }//GEN-LAST:event_txtForPesquisarMouseClicked

    private void txtForPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtForPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtForPesquisarActionPerformed

    private void txtForPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtForPesquisarKeyReleased
        // chama o método pesquisar clientes
        pesquisar_funcionarios();
    }//GEN-LAST:event_txtForPesquisarKeyReleased

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // Adicionar -- Chamar o método adicionar
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void tblFornecedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFornecedoresMouseClicked
        // chamando o método para setar os campos
        setar_campos();
    }//GEN-LAST:event_tblFornecedoresMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // Alterar -- chamar medtodo alterar
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        // Remover -- chamar o método remover
        remover();
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void cboForPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboForPagamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboForPagamentoActionPerformed

    private void txtForCNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtForCNPJActionPerformed

    }//GEN-LAST:event_txtForCNPJActionPerformed

    private void txtForCNPJKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtForCNPJKeyTyped
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtForCNPJKeyTyped

    private void txtForInscriçãoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtForInscriçãoKeyTyped
        //Irá Aceitar Apenas Números
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtForInscriçãoKeyTyped

    private void txtForFoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtForFoneKeyTyped
        // Irá aceitar apenas números
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtForFoneKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JComboBox<String> cboForPagamento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFornecedores;
    private javax.swing.JTextField txtForCNPJ;
    private javax.swing.JTextField txtForCidade;
    private javax.swing.JTextField txtForEmail;
    private javax.swing.JTextField txtForEndereco;
    private javax.swing.JTextField txtForFone;
    private javax.swing.JTextField txtForID;
    private javax.swing.JTextField txtForInscrição;
    private javax.swing.JTextField txtForNome;
    private javax.swing.JTextField txtForPesquisar;
    // End of variables declaration//GEN-END:variables
}
