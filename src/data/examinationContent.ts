import { ExaminationItem, Category } from '../types';

export const CATEGORIES: Category[] = [
  {
    id: 'deus',
    title: 'Relação com Deus',
    description: '1. Da minha relação com Deus',
  },
  {
    id: 'proximo',
    title: 'Relação com o Próximo',
    description: '2. Da minha relação para com meu próximo',
  },
  {
    id: 'consigo',
    title: 'Respeito para comigo',
    description: '3. Do meu respeito para comigo',
  },
];

export const EXAMINATION_ITEMS: ExaminationItem[] = [
  // 1. Da minha relação com Deus
  { id: 'd1', category: 'deus', text: 'Se tenho rezado diariamente, e se rezei com devoção.' },
  { id: 'd2', category: 'deus', text: 'Se perdi a esperança em Deus, sobretudo nas dificuldades.' },
  { id: 'd3', category: 'deus', text: 'Se reclamei da vontade de Deus nas dificuldades.' },
  { id: 'd4', category: 'deus', text: 'Se deixei de ir à Missa nos domingos e dias de preceito.' },
  { id: 'd5', category: 'deus', text: 'Se deixei de cumprir o preceito pascal (comungar pela Páscoa).' },
  { id: 'd6', category: 'deus', text: 'Se passei muito tempo sem buscar o Sacramento da Confissão. Quanto tempo?' },
  { id: 'd7', category: 'deus', text: 'Se trabalhei nos domingos e dias santos de guarda sem justa causa.' },
  { id: 'd8', category: 'deus', text: 'Se cheguei atrasado por culpa própria à Missa.' },
  { id: 'd9', category: 'deus', text: 'Se tenho prestado atenção e tido a devida compostura durante a Missa e dentro da igreja.' },
  { id: 'd10', category: 'deus', text: 'Se usei roupas indecorosas para ir à Igreja.' },
  { id: 'd11', category: 'deus', text: 'Se escondi algum pecado grave na confissão por vergonha ou outro motivo, ou me confessei sem dor dos pecados.' },
  { id: 'd12', category: 'deus', text: 'Se pequei pensando na facilidade da confissão.' },
  { id: 'd13', category: 'deus', text: 'Se comunguei estando em pecado grave.' },
  { id: 'd14', category: 'deus', text: 'Se deixei de guardar o jejum e a abstinência de carne nos dias prescritos pela Santa Igreja.' },
  { id: 'd15', category: 'deus', text: 'Tenho sido infiel à Religião, duvidando da Fé, lendo escritos ou assistindo programas de televisão contrários à Igreja Católica?' },
  { id: 'd16', category: 'deus', text: 'Se tive vergonha de manifestar a minha fé católica.' },
  { id: 'd17', category: 'deus', text: 'Se deixei de ajudar a Igreja em suas necessidades, mesmo materiais, com o dízimo, tendo condições para isso.' },
  { id: 'd18', category: 'deus', text: 'Se frequentei outras religiões, seitas, ou práticas supersticiosas, como magias, feitiços, adivinhações etc.' },
  { id: 'd19', category: 'deus', text: 'Se pronunciei o nome de Deus, de Maria Santíssima ou dos santos sem respeito.' },
  { id: 'd20', category: 'deus', text: 'Se jurei por Deus ou jurei falso.' },

  // 2. Da minha relação para com meu próximo
  { id: 'p1', category: 'proximo', text: 'Se desrespeitei ou desobedeci a meus pais e superiores.' },
  { id: 'p2', category: 'proximo', text: 'Se ofendi meu próximo, e se a ofensa foi grave.' },
  { id: 'p3', category: 'proximo', text: 'Se fui relaxado na educação de meus filhos e se me empenhei em transmitir-lhes a Fé Católica.' },
  { id: 'p4', category: 'proximo', text: 'Se deixei de corrigi-los ou dei-lhes mau exemplo.' },
  { id: 'p5', category: 'proximo', text: 'Se tratei mal minha esposa (meu esposo).' },
  { id: 'p6', category: 'proximo', text: 'Se falei ou pensei mal de alguém.' },
  { id: 'p7', category: 'proximo', text: 'Se disse palavras injuriosas ou baixas.' },
  { id: 'p8', category: 'proximo', text: 'Se falei mentiras, leves ou graves, e se prejudiquei ao próximo com elas.' },
  { id: 'p9', category: 'proximo', text: 'Se cometi defeitos ou faltas de meu próximo sem necessidade.' },
  { id: 'p10', category: 'proximo', text: 'Se fiz intrigas ou caluniei alguém em coisas graves.' },
  { id: 'p11', category: 'proximo', text: 'Se irritei-me ou roguei pragas a alguém, e se recusei reconciliar-me, guardando rancor.' },
  { id: 'p12', category: 'proximo', text: 'Se desejei ou cometi vinganças.' },
  { id: 'p13', category: 'proximo', text: 'Se desejei o mal a alguém, e se desejei a morte a alguém.' },
  { id: 'p14', category: 'proximo', text: 'Se agredi fisicamente alguém, ou matei alguém.' },
  { id: 'p15', category: 'proximo', text: 'Se usei meios imorais para “controlar” a gravidez.' },
  { id: 'p16', category: 'proximo', text: 'Se cometi um aborto, ou ajudei de alguma forma alguém a abortar?', note: 'A Santa Igreja ensina que o aborto provocado, qualquer que seja o método, é um pecado gravíssimo que leva consigo a pena canônica de excomunhão para quem o realiza e para todos os que nele tomam parte conscientemente.' },
  { id: 'p17', category: 'proximo', text: 'Se alegrei-me com a desgraça de meu próximo.' },
  { id: 'p18', category: 'proximo', text: 'Se descuidei meus deveres de estudos (trabalho, estudos, família).' },
  { id: 'p19', category: 'proximo', text: 'Se cobicei os bens alheios.' },
  { id: 'p20', category: 'proximo', text: 'Se roubei alguma coisa, qual o valor, e se já restituí.' },
  { id: 'p21', category: 'proximo', text: 'Se comprei coisas roubadas, e se ainda as tenho comigo.' },
  { id: 'p22', category: 'proximo', text: 'Se causei prejuízo grave a alguém e ainda não paguei. Se comprei e não paguei.' },
  { id: 'p23', category: 'proximo', text: 'Se pedi emprestado e não devolvi.' },
  { id: 'p24', category: 'proximo', text: 'Se levei outros a pecar, por mau exemplo ou por convite.' },
  { id: 'p25', category: 'proximo', text: 'Se mostrei imagens ou vídeos contrários à castidade a outros.' },
  { id: 'p26', category: 'proximo', text: 'Se olhei para alguém com intenções impuras.' },
  { id: 'p27', category: 'proximo', text: 'Se fiz gestos ou disse palavras contrários à castidade.' },

  // 3. Do meu respeito para comigo
  { id: 'c1', category: 'consigo', text: 'Se tive consciência de que meu corpo é um “templo do Espírito Santo”, e se tomei alguma atitude contrária a esse altíssimo dom que possuo.' },
  { id: 'c2', category: 'consigo', text: 'Se consenti em olhar ou pensar coisas contrárias à castidade.' },
  { id: 'c3', category: 'consigo', text: 'Se conversei, li ou assisti coisas contrárias à castidade (TV, Internet, celular, etc.).' },
  { id: 'c4', category: 'consigo', text: 'Se deixei de fugir dos desejos de coisas contrárias à castidade.' },
  { id: 'c5', category: 'consigo', text: 'Se cometi atos impuros (sozinho, com outras pessoas, solteiros ou casados).' },
  { id: 'c6', category: 'consigo', text: 'Se tenho fugido das más ocasiões.' },
  { id: 'c7', category: 'consigo', text: 'Se usei roupas indecentes.' },
  { id: 'c8', category: 'consigo', text: 'Se participei de divertimentos contrários à castidade.' },
  { id: 'c9', category: 'consigo', text: 'Se traí, ainda que por pensamentos ou desejos, minha esposa (meu esposo).' },
  { id: 'c10', category: 'consigo', text: 'Se desejei ou procurei minha morte.' },
  { id: 'c11', category: 'consigo', text: 'Se caí no vício do jogo, ou algum outro vício.' },
  { id: 'c12', category: 'consigo', text: 'Se cometi o pecado de gula comendo ou bebendo demais, e se me embriaguei.' },
  { id: 'c13', category: 'consigo', text: 'Se fui egoísta, orgulhoso ou vaidoso, chamando a atenção sobre mim, ou olhando-me demoradamente no espelho.' },
  { id: 'c14', category: 'consigo', text: 'Se fui invejoso, entristecendo-me com as qualidades ou bens de meu próximo.' },
];

export const PRAYERS = {
  PREPARATION: {
    title: 'Para fazer uma boa confissão',
    items: [
      'Ter confiança no perdão de Deus',
      'Fazer bem o exame de consciência',
      'Ter dor e arrependimento dos próprios pecados',
      'Fazer o propósito de nunca mais os cometer',
      'Dizer os próprios pecados ao confessor, sem esconder nenhum, evitando divagações',
      'Referir, em relação aos pecados graves, quanto possível, o seu número',
      'Aceitar a penitência imposta pelo confessor',
    ]
  },
  COLLECTION: {
    title: 'Recolha-se em silêncio na presença de Deus:',
    text: '“Meu Deus e Senhor, eu me preparo para receber o santo sacramento da Penitência. Iluminai o meu espírito, a fim de que eu conheça claramente o número e a gravidade dos meus pecados, deles me arrependa, e os confesse ao vosso ministro com verdadeira dor e firme propósito de nunca mais Vos tornar a ofender. Amém”.'
  },
  ACUSACAO: {
    title: 'Acusação das faltas',
    description: 'Terminada a enumeração dos pecados, diga ao confessor:',
    text: '“Destes e de todos os pecados da minha vida me arrependo de todo o meu coração. Quero emendar-me e peço perdão de minhas culpas. Arrependo-me também de todos os pecados de que não tenho memória nem conhecimento”.'
  },
  CONTRICAO: {
    title: 'Ato de contrição',
    text: '“Meu Pai e meu Deus, reconheço com muita dor que pequei! Eu Vos ofendi, ó meu Redentor, e mereci os vossos castigos. Por Maria, minha e vossa Mãe, declaro que não quero pecar mais. Por Ela, eu Vos peço perdão”.'
  },
  NOSSA_SENHORA: {
    title: 'Oração a Nossa Senhora',
    text: '“Ó Mãe boníssima, não me esqueças quando eu de Vós me esqueça; não me abandones quando eu Vos abandone; segui-me com vosso celeste olhar e chamai-me quando eu me afaste de Vós; procurai-me quando eu me esconda; ide ao meu encalço quando eu fuja; atai-me quando eu Vos resista; dominai-me caso eu me ponha de pé contra Vós; levantai-me quando eu caia; reconduzi-me pelo vosso caminho quando eu me transvie”.',
    author: 'Plinio Corrêa de Oliveira'
  }
};
