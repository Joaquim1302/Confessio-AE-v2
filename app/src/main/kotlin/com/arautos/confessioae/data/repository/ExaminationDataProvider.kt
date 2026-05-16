package com.arautos.confessioae.data.repository

import com.arautos.confessioae.data.model.Category
import com.arautos.confessioae.data.model.ExaminationItem

/**
 * Provedor de dados estáticos para o Exame de Consciência.
 * 
 * Fonte dos Dados: O conteúdo textual é baseado no exame de consciência dos Arautos do Evangelho.
 * A estrutura em lista flat facilita a filtragem dinâmica por categoria nas telas de UI.
 */
object ExaminationDataProvider {
    val items = listOf(
        // Relations with God
        ExaminationItem("d1", "Se tenho rezado diariamente, e se rezei com devoção.", Category.DEUS),
        ExaminationItem("d2", "Se perdi a esperança em Deus, sobretudo nas dificuldades.", Category.DEUS),
        ExaminationItem("d3", "Se reclamei da vontade de Deus nas dificuldades.", Category.DEUS),
        ExaminationItem("d4", "Se deixei de ir à Missa nos domingos e dias de preceito.", Category.DEUS),
        ExaminationItem("d5", "Se deixei de cumprir o preceito pascal (comungar pela Páscoa).", Category.DEUS),
        ExaminationItem("d6", "Se passei muito tempo sem buscar o Sacramento da Confissão. Quanto tempo?", Category.DEUS),
        ExaminationItem("d7", "Se trabalhei nos domingos e dias santos de guarda sem justa causa.", Category.DEUS),
        ExaminationItem("d8", "Se cheguei atrasado por culpa própria à Missa.", Category.DEUS),
        ExaminationItem("d9", "Se tenho prestado atenção e tido a devida compostura durante a Missa e dentro da igreja.", Category.DEUS),
        ExaminationItem("d10", "Se used roupas indecorosas para ir à Igreja.", Category.DEUS),
        ExaminationItem("d11", "Se escondi algum pecado grave na confissão por vergonha ou outro motivo, ou me confessei sem dor dos pecados.", Category.DEUS),
        ExaminationItem("d12", "Se pequei pensando na facilidade da confissão.", Category.DEUS),
        ExaminationItem("d13", "Se comunguei estando em pecado grave.", Category.DEUS),
        ExaminationItem("d14", "Se deixei de guardar o jejum e a abstinência de carne nos dias prescritos pela Santa Igreja.", Category.DEUS),
        ExaminationItem("d15", "Tenho sido infiel à Religião, duvidando da Fé, lendo escritos ou assistindo programas de televisão contrários à Igreja Católica?", Category.DEUS),
        ExaminationItem("d16", "Se tive vergonha de manifestar a minha fé católica.", Category.DEUS),
        ExaminationItem("d17", "Se deixei de ajudar a Igreja em suas necessidades, mesmo materiais, com o dízimo, tendo condições para isso.", Category.DEUS),
        ExaminationItem("d18", "Se frequentei outras religiões, seitas, ou práticas supersticiosas, como magias, feitiços, adivinhações etc.", Category.DEUS),
        ExaminationItem("d19", "Se pronunciei o nome de Deus, de Maria Santíssima ou dos santos sem respeito.", Category.DEUS),
        ExaminationItem("d20", "Se jurei por Deus ou jurei falso.", Category.DEUS),

        // Relations with Next
        ExaminationItem("p1", "Se desrespeitei ou desobedeci a meus pais e superiores.", Category.PROXIMO),
        ExaminationItem("p2", "Se ofendi meu próximo, e se a ofensa foi grave.", Category.PROXIMO),
        ExaminationItem("p3", "Se fui relaxado na educação de meus filhos e se me empenhei em transmitir-lhes a Fé Católica.", Category.PROXIMO),
        ExaminationItem("p4", "Se deixei de corrigi-los ou dei-lhes mau exemplo.", Category.PROXIMO),
        ExaminationItem("p5", "Se tratei mal minha esposa (meu esposo).", Category.PROXIMO),
        ExaminationItem("p6", "Se falei ou pensei mal de alguém.", Category.PROXIMO),
        ExaminationItem("p7", "Se disse palavras injuriosas ou baixas.", Category.PROXIMO),
        ExaminationItem("p8", "Se falei mentiras, leves ou graves, e se prejudiquei ao próximo com elas.", Category.PROXIMO),
        ExaminationItem("p9", "Se comentei defeitos ou faltas de meu próximo sem necessidade.", Category.PROXIMO),
        ExaminationItem("p10", "Se fiz intrigas ou caluniei alguém em coisas graves.", Category.PROXIMO),
        ExaminationItem("p11", "Se irritei-me ou roguei pragas a alguém, e se recusei reconciliar-me, guardando rancor.", Category.PROXIMO),
        ExaminationItem("p12", "Se desejei ou cometi vinganças.", Category.PROXIMO),
        ExaminationItem("p13", "Se desejei o mal a alguém, e se desejei a morte a alguém.", Category.PROXIMO),
        ExaminationItem("p14", "Se agredi fisicamente alguém, ou matei alguém.", Category.PROXIMO),
        ExaminationItem("p15", "Se usei meios imorais para “controlar” a gravidez.", Category.PROXIMO),
        ExaminationItem("p16", "Se cometi um aborto, ou ajudei de alguma forma alguém a abortar?", Category.PROXIMO, "A Santa Igreja ensina que o aborto provocado, qualquer que seja o método, é um pecado gravíssimo que leva consigo a pena canônica de excomunhão para quem o realiza e para todos os que nele tomam parte conscientemente."),
        ExaminationItem("p17", "Se alegrei-me com a desgraça de meu próximo.", Category.PROXIMO),
        ExaminationItem("p18", "Se descuidei meus deveres de estudos (trabalho, estudos, família).", Category.PROXIMO),
        ExaminationItem("p19", "Se cobicei os bens alheios.", Category.PROXIMO),
        ExaminationItem("p20", "Se roubei alguma coisa, qual o valor, e se já restituí.", Category.PROXIMO),
        ExaminationItem("p21", "Se comprei coisas roubadas, e se ainda as tenho comigo.", Category.PROXIMO),
        ExaminationItem("p22", "Se causei prejuízo grave a alguém e ainda não paguei. Se comprei e não paguei.", Category.PROXIMO),
        ExaminationItem("p23", "Se pedi emprestado e não devolvi.", Category.PROXIMO),
        ExaminationItem("p24", "Se levei outros a pecar, por mau exemplo ou por convite.", Category.PROXIMO),
        ExaminationItem("p25", "Se mostrei imagens ou vídeos contrários à castidade a outros.", Category.PROXIMO),
        ExaminationItem("p26", "Se olhei para alguém com intenções impuras.", Category.PROXIMO),
        ExaminationItem("p27", "Se fiz gestos ou disse palavras contrários à castidade.", Category.PROXIMO),

        // Self respect
        ExaminationItem("c1", "Se tive consciência de que meu corpo é um “templo do Espírito Santo”, e se tomei alguma atitude contrária a esse altíssimo dom que possuo.", Category.CONSIGO),
        ExaminationItem("c2", "Se consenti em olhar ou pensar coisas contrárias à castidade.", Category.CONSIGO),
        ExaminationItem("c3", "Se conversei, li ou assisti coisas contrárias à castidade (TV, Internet, celular, etc.).", Category.CONSIGO),
        ExaminationItem("c4", "Se deixei de fugir dos desejos de coisas contrárias à castidade.", Category.CONSIGO),
        ExaminationItem("c5", "Se cometi atos impuros (sozinho, com outras pessoas, solteiros ou casados).", Category.CONSIGO),
        ExaminationItem("c6", "Se tenho fugido das más ocasiões.", Category.CONSIGO),
        ExaminationItem("c7", "Se usei roupas indecentes.", Category.CONSIGO),
        ExaminationItem("c8", "Se participei de divertimentos contrários à castidade.", Category.CONSIGO),
        ExaminationItem("c9", "Se traí, ainda que por pensamentos ou desejos, minha esposa (meu esposo).", Category.CONSIGO),
        ExaminationItem("c10", "Se desejei ou procurei minha morte.", Category.CONSIGO),
        ExaminationItem("c11", "Se caí no vício do jogo, ou algum outro vício.", Category.CONSIGO),
        ExaminationItem("c12", "Se cometi o pecado de gula comendo ou bebendo demais, e se me embriaguei.", Category.CONSIGO),
        ExaminationItem("c13", "Se fui egoísta, orgulhoso ou vaidoso, chamando a atenção sobre mim, ou olhando-me demoradamente no espelho.", Category.CONSIGO),
        ExaminationItem("c14", "Se fui invejoso, entristecendo-me com as qualidades ou bens de meu próximo.", Category.CONSIGO),
    )
}
