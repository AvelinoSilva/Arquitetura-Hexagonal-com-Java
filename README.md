Sistema de Gerenciamento de Clínica Veterinária
# Arquitetura-Hexagonal-com-Java

Este projeto foi desenvolvido para demonstrar os conceitos de Arquitetura Hexagonal (Ports and Adapters) utilizando Java puro, sem uso de frameworks externos. O objetivo principal é separar claramente as regras de negócio da infraestrutura, permitindo que o domínio permaneça independente de tecnologias externas.

Estrutura do Projeto

com.clinica

dominio

modelo
excecao
porta
entrada
saida
servico

infraestrutura

adaptador
persistencia
notificacao

apresentacao

Main

O pacote dominio contém o núcleo da aplicação e não depende de nenhuma outra camada.
O pacote infraestrutura contém as implementações concretas das portas definidas no domínio.
O pacote apresentacao contém a classe Main que monta a aplicação e executa o fluxo.

Decisões Arquiteturais

O projeto foi estruturado seguindo os princípios da Arquitetura Hexagonal. O domínio contém apenas regras de negócio e contratos (interfaces). Toda dependência externa é isolada por meio de portas e adaptadores.

As principais decisões arquiteturais foram:

Separação total do domínio
O pacote dominio contém entidades, exceções, portas e serviços. Nenhuma classe desse pacote depende de classes de infraestrutura.
Uso de portas (interfaces)
As portas definem contratos entre o domínio e o mundo externo. Isso permite trocar implementações sem alterar as regras de negócio.
Injeção de dependência por construtor
O serviço ServicoAgendaConsulta recebe todas as portas necessárias no construtor. Dessa forma o domínio não instancia diretamente dependências externas.
Uso de Optional
Os métodos de busca nos repositórios retornam Optional para evitar retornos nulos e obrigar tratamento explícito de ausência de dados.

Portas Implementadas

PortaAgendaConsulta (porta de entrada)

Define os casos de uso do sistema relacionados à agenda de consultas.

Métodos principais:

agendarConsulta
realizarConsulta
cancelarConsulta
obterHistoricoAnimal
obterAgendaVeterinario

Implementação:
ServicoAgendaConsulta

PortaAnimalRepositorio (porta de saída)

Responsável pelo acesso e armazenamento de dados de animais.

Métodos:

salvar
buscarPorId
listarPorTutor
listarTodos
remover

Adaptador implementado:
AnimalRepositorioMemoria

PortaVeterinarioRepositorio (porta de saída)

Responsável pelo armazenamento e busca de veterinários.

Métodos:

salvar
buscarPorId
buscarDisponiveis
buscarPorEspecialidade

Adaptador implementado:
VeterinarioRepositorioMemoria

PortaConsultaRepositorio (porta de saída)

Responsável pela persistência das consultas.

Métodos:

salvar
buscarPorId
buscarPorAnimal
buscarPorVeterinario
listarAgendadas

Adaptador implementado:
ConsultaRepositorioMemoria

PortaNotificacaoTutor (porta de saída)

Responsável por enviar notificações ao tutor do animal.

Métodos:

notificarAgendamento
notificarCancelamento

Adaptadores implementados:
NotificacaoConsole
NotificacaoCsv

O uso de dois adaptadores demonstra a capacidade da arquitetura de trocar implementações sem alterar o domínio.

Adaptadores Implementados

Adaptadores de Persistência

Localizados em:
infraestrutura/adaptador/persistencia

Implementações:

AnimalRepositorioMemoria
VeterinarioRepositorioMemoria
ConsultaRepositorioMemoria

Esses adaptadores utilizam estruturas em memória (HashMap) para simular persistência de dados.

Adaptadores de Notificação

Localizados em:
infraestrutura/adaptador/notificacao

Implementações:

NotificacaoConsole
Responsável por exibir mensagens de notificação no terminal.

NotificacaoCsv
Responsável por registrar notificações em um arquivo CSV chamado notificacoes.csv.

A troca entre esses adaptadores ocorre apenas na classe Main.

Compilação do Projeto

A compilação deve ser realizada a partir do diretório src.

Comando:

find . -name "*.java" > sources.txt
javac -d out @sources.txt

Execução do Projeto

Após a compilação, execute o seguinte comando:

java -cp out com.clinica.apresentacao.Main

Execução de Testes (Opcional)

Caso existam classes de teste no pacote test, os testes podem ser executados com assertions habilitadas.

Comando:

java -ea -cp out com.clinica.test.TestesDominio

Fluxo Demonstrado no Main

A classe Main demonstra o funcionamento do sistema realizando as seguintes ações:

Cadastro de animais
Cadastro de veterinários
Agendamento de consulta
Realização de consulta
Cancelamento de consulta
Consulta de histórico de consultas
Consulta da agenda de um veterinário
