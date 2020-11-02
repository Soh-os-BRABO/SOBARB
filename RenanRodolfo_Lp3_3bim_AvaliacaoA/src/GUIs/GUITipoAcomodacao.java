////////////GUI////////////
package GUIs;

import DAOs.DAOTipoAcomodacao;
import Entidades.TipoAcomodacao;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import tools.ManipulaArquivo;

import tools.DateTextField;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import tools.Centraliza;

public class GUITipoAcomodacao extends JDialog {

    private Container cp;

    private JLabel lbTipoAcomodacao = new JLabel("ID Tipo da Acomodação");
    private JLabel lbNomeAcomodacao = new JLabel("Nome do tipo do acomodação");

    private JTextField tfTipoAcomodacao = new JTextField(50);
    private JTextField tfNomeAcomodacao = new JTextField(50);

    private JButton btAdicionar = new JButton("Adicionar");
    private JButton btListar = new JButton("Listar");
    private JButton btBuscar = new JButton("Buscar");
    private JButton btAlterar = new JButton("Alterar");
    private JButton btExcluir = new JButton("Excluir");
    private JButton btSalvar = new JButton("Salvar");
    private JButton btCancelar = new JButton("Cancelar");
    private JButton btCarregarDados = new JButton("Carregar");
    private JButton btGravar = new JButton("Gravar");
    private JToolBar toolBar = new JToolBar();
    private JPanel painelNorte = new JPanel();
    private JPanel painelCentro = new JPanel();
    private JPanel painelSul = new JPanel();
    private JTextArea texto = new JTextArea();
    private JScrollPane scrollTexto = new JScrollPane();
    private JScrollPane scrollTabela = new JScrollPane();

    private String acao = "";
    private String chavePrimaria = "";

    private DAOTipoAcomodacao controle = new DAOTipoAcomodacao();
    private TipoAcomodacao entidade = new TipoAcomodacao();

    String[] colunas = new String[]{"ID", "TipoAcomodação"};
    String[][] dados = new String[0][2];
    DefaultTableModel model = new DefaultTableModel(dados, colunas);
    JTable tabela = new JTable(model);

    private JPanel painel1 = new JPanel(new GridLayout(1, 1));
    private JPanel painel2 = new JPanel(new GridLayout(1, 1));
    private CardLayout cardLayout;
    private JDialog jdialog;

    public GUITipoAcomodacao(JFrame pai) {

        jdialog = new JDialog(pai, "Acomodação", true);
        jdialog.getContentPane();
        jdialog.pack();
        jdialog.setTitle("TipoAcomodação");
        jdialog.setSize(600, 400);
        jdialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jdialog.setLayout(new GridLayout(1, 1));
        Centraliza centraliza = new Centraliza();
        centraliza.centralizaFilho(pai, jdialog);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(600, 300);
        setTitle("CRUD Canguru - V6a");
        setLocationRelativeTo(null);//centro do monitor

        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(painelNorte, BorderLayout.NORTH);
        cp.add(painelCentro, BorderLayout.CENTER);
        cp.add(painelSul, BorderLayout.SOUTH);

        cardLayout = new CardLayout();
        painelSul.setLayout(cardLayout);

        painel1.add(scrollTexto);
        painel2.add(scrollTabela);

        texto.setText("\n\n\n\n\n\n");//5 linhas de tamanho
        scrollTexto.setViewportView(texto);

        painelSul.add(painel1, "Avisos");
        painelSul.add(painel2, "Listagem");
        tabela.setEnabled(false);

        painelNorte.setLayout(new GridLayout(1, 1));
        painelNorte.add(toolBar);

        painelCentro.setLayout(new GridLayout(1, 2));

        painelCentro.add(lbNomeAcomodacao);
        painelCentro.add(tfNomeAcomodacao);

        toolBar.add(lbTipoAcomodacao);
        toolBar.add(tfTipoAcomodacao);
        toolBar.add(btAdicionar);
        toolBar.add(btBuscar);
        toolBar.add(btListar);
        toolBar.add(btAlterar);
        toolBar.add(btExcluir);
        toolBar.add(btSalvar);
        toolBar.add(btCancelar);

        btAdicionar.setVisible(false);
        btAlterar.setVisible(false);
        btExcluir.setVisible(false);
        btSalvar.setVisible(false);
        btCancelar.setVisible(false);

        tfNomeAcomodacao.setEditable(false);
        texto.setEditable(false);
        jdialog.add(cp);

        btBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btAdicionar.setVisible(false);

                cardLayout.show(painelSul, "Avisos");
                scrollTexto.setViewportView(texto);
                if (tfTipoAcomodacao.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(cp, "ID nâo pode ser vazio");
                    tfTipoAcomodacao.requestFocus();
                    tfTipoAcomodacao.selectAll();
                } else {
                    chavePrimaria = tfTipoAcomodacao.getText();//para uso no adicionar
                    entidade = controle.obter(Integer.valueOf(tfTipoAcomodacao.getText()));
                    if (entidade == null) {//nao encontrou
                        btAdicionar.setVisible(true);
                        btAlterar.setVisible(false);
                        btExcluir.setVisible(false);
                        tfNomeAcomodacao.setText("");
                        texto.setText("Não encontrou na lista - pode Adicionar\n\n\n");//limpa o campo texto

                    } else {//encontrou
                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                        tfNomeAcomodacao.setText(entidade.getNomeTipo());

                        btAlterar.setVisible(true);
                        btExcluir.setVisible(true);
                        texto.setText("Encontrou na lista - pode Alterar ou Excluir\n\n\n");//limpa o campo texto

                        tfNomeAcomodacao.setEditable(false);
                    }
                }
            }
        });
        btAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acao = "adicionar";
                tfTipoAcomodacao.setText(chavePrimaria);//para retornar ao valor original (caso o usuário mude e tente enganar o programa)
                tfTipoAcomodacao.setEditable(false);
                tfNomeAcomodacao.requestFocus();
                btSalvar.setVisible(true);
                btCancelar.setVisible(true);
                btBuscar.setVisible(false);
                btListar.setVisible(false);
                btAlterar.setVisible(false);
                btExcluir.setVisible(false);

                btAdicionar.setVisible(false);
                texto.setText("Preencha os atributos\n\n\n\n\n");//limpa o campo texto
                tfNomeAcomodacao.setEditable(true);
            }
        });

        btAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acao = "alterar";
                tfTipoAcomodacao.setText(chavePrimaria);//para retornar ao valor original (caso o usuário mude e tente enganar o programa)
                tfTipoAcomodacao.setEditable(false);
                tfNomeAcomodacao.requestFocus();
                btSalvar.setVisible(true);
                btCancelar.setVisible(true);
                btBuscar.setVisible(false);
                btListar.setVisible(false);
                btAlterar.setVisible(false);
                btExcluir.setVisible(false);
                texto.setText("Preencha os atributos\n\n\n\n\n");//limpa o campo texto
                tfNomeAcomodacao.setEditable(true);
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btSalvar.setVisible(false);
                btCancelar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                tfTipoAcomodacao.setEditable(true);

                tfNomeAcomodacao.setText("");

                tfTipoAcomodacao.requestFocus();
                tfTipoAcomodacao.selectAll();
                texto.setText("Cancelou\n\n\n\n\n");//limpa o campo texto

                tfNomeAcomodacao.setEditable(false);
            }
        });

        btSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (acao.equals("alterar")) {
                    TipoAcomodacao entidadeAntigo = entidade;
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdfEua = new SimpleDateFormat("yyyy-MM-dd");

                    entidade.setNomeTipo(tfNomeAcomodacao.getText());

                    controle.atualizar(entidade);
                    texto.setText("Registro alterado\n\n\n\n\n");//limpa o campo texto
                } else {//adicionar
                    entidade = new TipoAcomodacao();
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdfEua = new SimpleDateFormat("yyyy-MM-dd");

                    entidade.setIdTipo(Integer.valueOf(tfTipoAcomodacao.getText()));
                    entidade.setNomeTipo(tfNomeAcomodacao.getText());

                    controle.inserir(entidade);
                    texto.setText("Foi adicionado um novo registro\n\n\n\n\n");//limpa o campo texto
                }
                btSalvar.setVisible(false);
                btCancelar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                tfTipoAcomodacao.setEditable(true);

                tfNomeAcomodacao.setText("");

                tfTipoAcomodacao.requestFocus();
                tfTipoAcomodacao.selectAll();

                tfNomeAcomodacao.setEditable(false);
            }
        });

        btExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfTipoAcomodacao.setText(chavePrimaria);//para retornar ao valor original (caso o usuário mude e tente enganar o programa)
                if (JOptionPane.YES_OPTION
                        == JOptionPane.showConfirmDialog(null,
                                "Confirma a exclusão do registro <NomeEquipamento = " + entidade.getNomeTipo() + ">?", "Confirm",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    controle.remover(entidade);
                }
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                tfTipoAcomodacao.setEditable(true);

                tfNomeAcomodacao.setText("");

                tfTipoAcomodacao.requestFocus();
                tfTipoAcomodacao.selectAll();
                btExcluir.setVisible(false);
                btAlterar.setVisible(false);
                texto.setText("Excluiu o registro de " + entidade.getIdTipo() + " - " + entidade.getNomeTipo() + "\n\n\n\n\n");//limpa o campo texto
            }
        });
        btListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<TipoAcomodacao> lt = controle.listInOrderId();

                String[] colunas = {"ID", "TipoAcomodação"};
                Object[][] dados = new Object[lt.size()][colunas.length];
                String aux[];
                for (int i = 0; i < lt.size(); i++) {
                    aux = lt.get(i).toString().split(";");
                    for (int j = 0; j < colunas.length; j++) {
                        dados[i][j] = aux[j];
                    }
                }
                cardLayout.show(painelSul, "Listagem");
                scrollTabela.setPreferredSize(tabela.getPreferredSize());
                painel2.add(scrollTabela);
                scrollTabela.setViewportView(tabela);
                model.setDataVector(dados, colunas);
                btAlterar.setVisible(false);
                btExcluir.setVisible(false);
                btAdicionar.setVisible(false);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //antes de sair, salvar a lista em disco
                // Sai da classe
                dispose();
            }
        });
        jdialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jdialog.dispose();

            }
        });

        jdialog.setVisible(true);

//depois que a tela ficou visível, clic o botão automaticamente.
    }

}
