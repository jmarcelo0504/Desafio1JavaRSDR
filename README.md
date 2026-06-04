# Rede Solidária de Doação e Reaproveitamento (RSDR)

Sistema de terminal em Java focado em sustentabilidade, reaproveitamento de recursos e impacto social. Permite o cadastro e controle de doadores, beneficiários, itens de doação, solicitações de itens e registro histórico de doações concluídas.

---

## Compromisso com os Objetivos de Desenvolvimento Sustentável (ODS)

Este projeto foi desenhado sob a perspectiva de impacto social direto e governança socioambiental, alinhando-se ativamente a quatro **Objetivos de Desenvolvimento Sustentável (ODS)** da Organização das Nações Unidas (ONU):

1. **ODS 1: Erradicação da Pobreza**
   - _Ação Prática:_ O sistema facilita a identificação e ordenação de beneficiários sob vulnerabilidade crítica (famílias, ONGs, abrigos e escolas), canalizando suprimentos básicos e doações para quem mais precisa por meio de priorização dinâmica de atendimento.
2. **ODS 2: Fome Zero e Agricultura Sustentável**
   - _Ação Prática:_ Permite o gerenciamento e redirecionamento de excedentes de alimentos não-perecíveis e recursos essenciais, evitando o desperdício e fortalecendo redes de segurança alimentar.

3. **ODS 10: Redução das Desigualdades**
   - _Ação Prática:_ A lógica do sistema implementa o algoritmo de comparação (`Comparable`), priorizando automaticamente beneficiários de acordo com critérios objetivos de prioridade de vulnerabilidade (escala de 1 a 5), garantindo justiça e equidade na distribuição.

4. **ODS 12: Consumo e Produção Responsáveis**
   - _Ação Prática:_ Fomenta a economia circular por meio da logística reversa e reaproveitamento de itens usados/semi-novos (móveis, roupas, eletrônicos), reduzindo a extração de matéria-prima e a geração de resíduos sólidos em aterros.

---

## Arquitetura e Tecnologia Utilizada

- **Linguagem:** Java 17+
- **Paradigma de Programação:** Orientação a Objetos (POO) com herança, polimorfismo, interfaces de comparação e encapsulamento estrito.
- **Persistência Relacional:** SQLite (banco de dados local baseado em arquivo `rsdr_db.db`), rodando de forma 100% embarcada e independente de servidores rodando em background.
- **Acesso ao Banco:** Conector JDBC nativo (através de `DatabaseConnection`).
- **Gerenciador de Build:** Maven (`pom.xml`).

---

## Estrutura do Projeto

```text
src/
 ├─ main/
 │   └─ Main.java
 ├─ model/
 │   ├─ Beneficiario.java
 │   ├─ Doador.java
 │   ├─ Usuario.java
 │   ├─ ItemDoacao.java
 │   ├─ Solicitacao.java
 │   ├─ DoacaoEfetivada.java
 │   ├─ StatusItem.java
 │   ├─ StatusSolicitacao.java
 │   └─ TipoBeneficiario.java
 ├─ repository/
 │   ├─ UsuarioRepository.java
 │   ├─ ItemRepository.java
 │   ├─ SolicitacaoRepository.java
 │   └─ DoacaoRepository.java
 ├─ service/
 │   └─ ServiceRSDR.java
 ├─ util/
 │   ├─ DatabaseConnection.java    ]
 │   ├─ DatabaseInitializer.java   ]
 │   ├─ Entrada.java
 │   ├─ Menu.java
 │   └─ Validacao.java
 └─ db.properties
```

---

## Funcionamento do Terminal

O terminal conta com as seguintes opções:

1. **Cadastrar Doador**: Lê nome, telefone (validado), e-mail (validado) e endereço.
2. **Cadastrar Beneficiário**: Permite selecionar tipos específicos e prioridades.
3. **Cadastrar Item de Doação**: Cadastra nome, categoria, descrição, quantidade e estado.
4. **Listar Doadores**: Exibe todos os doadores.
5. **Listar Beneficiários**: Exibe a lista ordenada por prioridade (urgência).
6. **Listar Itens**: Exibe todos os itens e seus status atuais.
7. **Filtrar Itens**: Exibe os itens de acordo com o status selecionado.
8. **Solicitar Item**: Cria solicitação pendente validando a quantidade do estoque.
9. **Listar Solicitações**: Exibe o status de cada solicitação.
10. **Aprovar Solicitação**: Deduz o estoque do item. Se zerado, muda o status do item para `RESERVADO`.
11. **Concluir Entrega**: Registra a doação efetivada no histórico e atualiza o item para `ENTREGUE`.
12. **Relatório de Doações Efetivadas**: Mostra o histórico de todas as doações finalizadas.
13. **Rejeitar/Cancelar Solicitação**: Atualiza o status para `REJEITADA` e libera a reserva do estoque de volta ao item.

    0 . **Sair**: Fecha conexões e finaliza a aplicação.

---

## Integrante

João Marcelo
