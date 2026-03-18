# Estética Premium 💆‍♀️✨

Estética Premium é um aplicativo Android moderno desenvolvido para facilitar o agendamento de serviços de estética e beleza. O app oferece uma experiência fluida para os usuários descobrirem serviços, escolherem profissionais e gerenciarem seus horários.

## 🚀 Funcionalidades

- **Autenticação de Usuários**: Login e cadastro integrados com Firebase.
- **Catálogo de Serviços**: Visualização detalhada dos tratamentos oferecidos.
- **Seleção de Profissionais**: Escolha o especialista de sua preferência para cada serviço.
- **Agendamento Inteligente**: Seleção de data e horário com interface intuitiva.
- **Lembretes Automáticos**: Notificações push enviadas 30 minutos antes do compromisso agendado.
- **Gestão de Agendamentos**: Lista completa de compromissos marcados para acompanhamento.

## 🛠 Tecnologias Utilizadas

- **Linguagem**: [Kotlin](https://kotlinlang.org/)
- **Arquitetura**: MVVM (Model-View-ViewModel)
- **UI**: XML com View Binding / Material Design
- **Navegação**: Jetpack Navigation Component
- **Backend**: Firebase Auth & Firestore (planejado/integrado)
- **Lembretes**: AlarmManager & Broadcast Receivers para notificações locais

## 📦 Estrutura do Projeto

- `ui/`: Fragmentos e ViewModels organizados por funcionalidade.
- `data/`: Repositórios e modelos de dados.
- `receiver/`: Receptores de sistema para gerenciamento de notificações.
- `res/navigation/`: Gráfico de navegação do aplicativo.

## 🔧 Como Executar

1. Clone o repositório.
2. Abra o projeto no **Android Studio**.
3. Sincronize o Gradle.
4. Execute no emulador ou dispositivo físico (Android 7.0+ recomendado).

---
Desenvolvido por Vitor C. Souza
