<h1>
  <img src="/app/src/main/res/drawable/icone_570.webp" alt="Ícone Confessio AE" width="80" align="left">
  Confessio AE
  <p></p>
</h1>
<p align="center">  
  <img src="https://img.shields.io/badge/Status-em%20Desenvolvimento-blue">
  <img src="https://img.shields.io/badge/plataforma-Android-green">
  <img src="https://img.shields.io/badge/Android-10%2B-green">
  <img src="https://img.shields.io/badge/IA-Gemini%20%7C%20ChatGPT%20%7C%20Copilot-purple">
</p>

**Confessio AE** é um assistente Android desenvolvido para auxiliar fiéis católicos na preparação e realização do Sacramento da Confissão. O aplicativo oferece um roteiro estruturado e acolhedor, focado na introspecção e na organização das faltas para um encontro frutuoso com a misericórdia divina.

---

## Funcionalidades

- **Exame de Consciência Guiado**: Baseado no texto original dos Arautos do Evangelho, o exame é dividido em categorias (Deus, Próximo e Consigo) para facilitar a reflexão.
- **Registro de Pecados e Dúvidas**: Permite selecionar pecados pré-definidos ou adicionar itens customizados (texto ou voz) que não constam na lista.
- **Observações Detalhadas**: Adicione notas específicas a cada pecado para lembrar de circunstâncias ou frequência durante a confissão.
- **Roteiro Litúrgico**: Uma tela dedicada que guia o usuário desde o momento da entrada no confessionário até a absolvição, incluindo fórmulas como o Ato de Contrição.
- **Exportação em PDF**: Gere um roteiro impresso em duas colunas, otimizado para papel A4, pensado para ser usado de forma discreta e prática dentro do confessionário.
- **Gestão de Penitência**: Controle visual para marcar a conclusão da penitência imposta pelo confessor.
- **Privacidade e Persistência**: Todos os dados são salvos localmente no dispositivo via DataStore. O aplicativo oferece uma opção de limpeza total para garantir a privacidade do fiel.
- **Tema Dinâmico**: Suporte total a modo claro, modo escuro e fontes dinâmicas para melhor legibilidade.

## Tecnologias Utilizadas
- **Ferramentas de IA**:
  -  Gemini
  -  ChatGPT
  - Copilot 
- **Linguagem**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Arquitetura moderna e declarativa)
- **Arquitetura**: MVVM (Model-View-ViewModel)
- **Persistência**: [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (Preferências e estados)
- **Geração de PDF**: `PdfDocument` nativo do Android com suporte a layout multi-coluna.
- **Injeção de Dependência**: ViewModel padrão com suporte a StateFlow.

## Screenshots

| Início                                               | Exame                                              | Roteiro                                                | Sobre                                              |
|:----------------------------------------------------:|:--------------------------------------------------:|:------------------------------------------------------:|:--------------------------------------------------:|
| ![Home](https://github.com/Joaquim1302/Confessio-AE-v2/blob/main/screenshots/dasboard.webp) | ![Exame](https://github.com/Joaquim1302/Confessio-AE-v2/blob/main/screenshots/exame.webp) | ![Roteiro](https://github.com/Joaquim1302/Confessio-AE-v2/blob/main/screenshots/roteiro.webp) | ![Sobre](https://github.com/Joaquim1302/Confessio-AE-v2/blob/main/screenshots/sobre.webp) |

## Futuro do Projeto

Recursos planejados ou considerados para versões futuras:
- melhorias de privacidade;
- autenticação biométrica;
- criptografia local;
- EncryptedSharedPreferences;
- Android Keystore;
- SQLCipher para dados locais sensíveis;
- bloqueio de tela interna;
- opções de limpeza de dados;
- versão para Apple iOS.

## Como baixar e rodar o projeto

1. Clone o repositório:
   
   ```bash
   git clone https://github.com/Joaquim1302/Confessio-AE-v2.git
   ```

2. Abra o projeto no **Android Studio (versão Ladybug ou superior)**.

3. Sincronize o Gradle e execute o aplicativo em um emulador ou dispositivo físico com Android 7.0 (API 24) ou superior.

## 📄 Créditos e Licença

- **Texto Original do Exame de Consciência**: [Arautos do Evangelho](https://www.arautos.org).
- **Desenvolvimento**: Joaquim Marques.
- **Licença**: Este projeto é distribuído apenas para fins religiosos e pedagógicos, sem fins lucrativos.

---

*"Vinde a Mim todos vós que estais cansados e oprimidos e Eu vos aliviarei" (Mt 11,28)*
