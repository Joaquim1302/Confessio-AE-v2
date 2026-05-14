import React from 'react';
import { BrowserRouter, Routes, Route, useNavigate, useLocation } from 'react-router-dom';
import { motion, AnimatePresence } from 'motion/react';
import { BookOpen, CheckCircle2, Info, Lock, Trash2, ChevronLeft, ChevronRight, Check, Shield } from 'lucide-react';
import { useExamination } from './hooks/useExamination';
import { EXAMINATION_ITEMS, PRAYERS, CATEGORIES } from './data/examinationContent';
import { clsx, type ClassValue } from 'clsx';
import { twMerge } from 'tailwind-merge';

function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

// --- Components ---

const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const navigate = useNavigate();
  const location = useLocation();

  const navItems = [
    { path: '/', label: 'Início', icon: BookOpen },
    { path: '/exame', label: 'Exame', icon: Shield },
    { path: '/lista', label: 'Lista', icon: CheckCircle2 },
    { path: '/sobre', label: 'Sobre', icon: Info },
  ];

  return (
    <div className="min-h-screen flex flex-col w-full md:max-w-5xl mx-auto bg-app-bg md:border-x border-app-border">
      {/* Header */}
      <header className="flex flex-col md:flex-row justify-between items-center px-6 md:px-12 py-6 md:py-8 border-b border-app-border bg-app-bg gap-4">
        <div className="flex items-center space-x-4 cursor-pointer" onClick={() => navigate('/')}>
          <div className="w-8 h-8 border border-app-accent flex items-center justify-center transform rotate-45 shrink-0">
            <div className="transform -rotate-45 text-app-accent text-xs font-bold font-serif">†</div>
          </div>
          <div>
            <h1 className="text-xl md:text-2xl tracking-[0.2em] uppercase font-light leading-none">Confessio AE</h1>
            <p className="text-[9px] md:text-[10px] font-sans uppercase tracking-[0.2em] text-app-muted mt-1">Exame de Consciência</p>
          </div>
        </div>
        <nav className="flex space-x-6 md:space-x-10 text-[10px] md:text-[11px] font-sans uppercase tracking-[0.2em] text-app-secondary">
          {navItems.map((item) => (
            <button
              key={item.path}
              onClick={() => navigate(item.path)}
              className={cn(
                "transition-all duration-300 pb-1 border-b border-transparent",
                location.pathname === item.path ? "text-app-accent border-app-accent" : "opacity-50 hover:opacity-100"
              )}
            >
              {item.label}
            </button>
          ))}
        </nav>
      </header>

      {/* Main Area */}
      <main className="flex-1 flex flex-col md:flex-row h-full overflow-hidden">
        <AnimatePresence mode="wait">
          <motion.div
            key={location.pathname}
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ duration: 0.3 }}
            className="flex-1 flex flex-col"
          >
            {children}
          </motion.div>
        </AnimatePresence>
      </main>

      {/* Footer */}
      <footer className="flex flex-col md:flex-row justify-between items-center px-6 md:px-12 py-4 md:py-6 border-t border-app-border bg-app-bg gap-4">
        <div className="flex items-center space-x-2">
          <span className="text-[9px] md:text-[10px] font-sans text-app-muted uppercase tracking-wider">Texto original:</span>
          <span className="text-[9px] md:text-[10px] font-sans text-app-ink font-bold uppercase tracking-wider">Arautos do Evangelho</span>
        </div>
        <div className="flex items-center space-x-6">
          <div className="flex items-center space-x-2">
            <div className="w-2 h-2 rounded-full bg-green-700 opacity-60"></div>
            <span className="text-[9px] md:text-[10px] font-sans text-app-muted uppercase tracking-widest">Modo Offline Ativo</span>
          </div>
          <span className="text-[9px] md:text-[10px] font-sans text-app-muted uppercase tracking-widest">v1.0 MVP</span>
        </div>
      </footer>
    </div>
  );
};

const PrayerSection: React.FC<{ title: string; text?: string; description?: string; items?: string[]; author?: string }> = ({ title, text, description, items, author }) => (
  <section className="mb-10 animate-fade-in border-b border-app-border pb-10 last:border-0 last:pb-0">
    <h3 className="text-xl font-light italic mb-6 text-app-ink">{title}</h3>
    {description && <p className="text-[10px] font-sans uppercase tracking-[0.2em] text-app-muted mb-4">{description}</p>}
    {text && <p className="prayer-text mb-6">“{text.replace(/[“”]/g, '')}”</p>}
    {items && (
      <ul className="space-y-4">
        {items.map((item, idx) => (
          <li key={idx} className="flex gap-4 text-base leading-relaxed">
            <span className="text-app-accent font-serif italic font-medium">{idx + 1}.</span>
            <span className="text-app-secondary">{item}</span>
          </li>
        ))}
      </ul>
    )}
    {author && <p className="text-right text-[11px] font-serif italic text-app-muted mt-4">— {author}</p>}
  </section>
);

// --- Pages ---

const HomePage: React.FC = () => {
  const navigate = useNavigate();
  return (
    <div className="flex-1 flex flex-col md:grid md:grid-cols-12 gap-0">
      <aside className="md:col-span-4 border-b md:border-b-0 md:border-r border-app-border bg-app-aside p-8 md:p-12 flex flex-col justify-center space-y-6 md:space-y-8">
        <div className="w-12 h-12 md:w-16 md:h-16 border border-app-accent flex items-center justify-center transform rotate-45 mb-4 self-center">
          <div className="transform -rotate-45 text-app-accent text-xl md:text-2xl font-serif">†</div>
        </div>
        <div className="text-center space-y-4">
          <h2 className="text-2xl md:text-4xl font-light tracking-tight italic font-infant">O Sacramento da Confissão</h2>
          <p className="text-app-muted text-[10px] md:text-xs uppercase tracking-[0.3em] font-medium leading-relaxed">
            “Vinde a Mim todos vós que estais cansados e oprimidos e Eu vos aliviarei” (Mt 11,28)
          </p>
        </div>
        <div className="bg-white/50 p-6 border border-app-border space-y-4 text-left">
          <h3 className="text-[10px] font-bold uppercase tracking-wider text-app-accent">Para fazer uma boa confissão</h3>
          <ul className="text-[11px] text-app-secondary space-y-2 font-serif italic">
            <li className="flex gap-2"><span className="text-app-accent">1</span> Ter confiança no perdão de Deus</li>
            <li className="flex gap-2"><span className="text-app-accent">2</span> Fazer bem o exame de consciência</li>
            <li className="flex gap-2"><span className="text-app-accent">3</span> Ter dor e arrependimento dos próprios pecados</li>
            <li className="flex gap-2"><span className="text-app-accent">4</span> Fazer o propósito de nunca mais os cometer</li>
            <li className="flex gap-2"><span className="text-app-accent">5</span> Dizer os próprios pecados ao confessor, sem esconder nenhum, evitando divagações</li>
            <li className="flex gap-2"><span className="text-app-accent">6</span> Referir, em relação aos pecados graves, quanto possível, o seu número</li>
            <li className="flex gap-2"><span className="text-app-accent">7</span> Aceitar a penitência imposta pelo confessor</li>
          </ul>
        </div>
        <div className="pt-4 md:pt-8 w-full max-w-xs mx-auto space-y-4">
          <button 
            onClick={() => navigate('/exame')}
            className="btn-primary"
          >
            Exame de Consciência
          </button>
          <button
            onClick={() => navigate('/sobre')}
            className="btn-primary"
          >
            Sobre o Aplicativo
          </button>
        </div>
      </aside>

      <div className="md:col-span-8 p-6 md:p-16 overflow-y-auto bg-white">
        <div className="max-w-2xl mx-auto space-y-12 md:space-y-16">
          <PrayerSection {...PRAYERS.PREPARATION} />
          <PrayerSection {...PRAYERS.COLLECTION} />
        </div>
      </div>
    </div>
  );
};

const ExaminationPage: React.FC = () => {
  const { selectedItems, toggleItem } = useExamination();
  const [currentStep, setCurrentStep] = React.useState(0);
  const navigate = useNavigate();

  const category = CATEGORIES[currentStep];
  const items = EXAMINATION_ITEMS.filter(i => i.category === category.id);

  const nextStep = () => {
    if (currentStep < CATEGORIES.length - 1) {
      setCurrentStep(prev => prev + 1);
      window.scrollTo(0, 0);
    } else {
      navigate('/lista');
    }
  };

  const prevStep = () => {
    if (currentStep > 0) {
      setCurrentStep(prev => prev - 1);
      window.scrollTo(0, 0);
    }
  };

  return (
    <div className="flex-1 flex flex-col md:grid md:grid-cols-12 gap-0 overflow-hidden h-full">
      <aside className="md:col-span-3 border-b md:border-b-0 md:border-r border-app-border bg-app-aside p-6 md:p-12 overflow-y-auto">
        <p className="text-[9px] md:text-[10px] font-sans uppercase tracking-[0.2em] text-app-muted mb-4 md:mb-8">Etapas</p>
        <div className="flex md:flex-col gap-2 md:gap-4 overflow-x-auto md:overflow-x-visible pb-2 md:pb-0">
          {CATEGORIES.map((cat, idx) => (
            <div 
              key={cat.id}
              onClick={() => setCurrentStep(idx)}
              className={cn(
                "p-3 md:p-5 transition-all duration-300 border min-w-[140px] md:min-w-0 cursor-pointer",
                currentStep === idx ? "bg-white border-app-border shadow-sm" : "border-transparent opacity-40 hover:opacity-100"
              )}
            >
              <p className="text-[8px] md:text-[10px] font-sans uppercase tracking-widest text-app-accent mb-1">Passo {idx + 1}</p>
              <p className="text-xs md:text-sm font-serif italic truncate md:whitespace-normal">{cat.title}</p>
            </div>
          ))}
        </div>
        
        <div className="hidden md:block mt-12 pt-12 border-t border-app-border">
          <button 
            onClick={() => navigate('/lista')}
            className="btn-secondary"
          >
            Ver Minha Lista ({selectedItems.length})
          </button>
        </div>
      </aside>

      <section className="md:col-span-9 p-6 md:p-16 overflow-y-auto bg-white flex flex-col">
        <div className="max-w-2xl mx-auto w-full space-y-8 md:space-y-12">
          <header className="space-y-2 border-b border-app-border pb-6 md:pb-8">
            <div className="flex items-center justify-between">
              <span className="text-[9px] md:text-[10px] uppercase tracking-[0.2em] font-sans font-bold text-app-accent">Categoria {currentStep + 1} de {CATEGORIES.length}</span>
              <button onClick={() => navigate('/lista')} className="md:hidden text-[9px] font-sans uppercase tracking-widest underline text-app-accent">
                Lista ({selectedItems.length})
              </button>
            </div>
            <h2 className="text-2xl md:text-4xl font-light italic">{category.title}</h2>
            <p className="text-app-muted text-[10px] md:text-xs uppercase tracking-widest">{category.description}</p>
          </header>

          <div className="space-y-4 md:space-y-6">
            {items.map((item) => (
              <button
                key={item.id}
                onClick={() => toggleItem(item.id)}
                className={cn(
                  "item-card w-full text-left flex items-start gap-4 md:gap-6 group",
                  selectedItems.includes(item.id) && "selected"
                )}
              >
                <div className={cn(
                  "geometric-checkbox mt-1 shrink-0",
                  selectedItems.includes(item.id) && "active"
                )}>
                  {selectedItems.includes(item.id) && <div className="geometric-checkbox-inner" />}
                </div>
                <div className="space-y-2 md:space-y-3 flex-1 min-w-0">
                  <span className={cn(
                    "text-sm md:text-lg leading-relaxed block font-serif transition-colors",
                    selectedItems.includes(item.id) ? "text-app-ink" : "text-app-secondary group-hover:text-app-ink"
                  )}>
                    {item.text}
                  </span>
                  {item.note && (
                    <div className="border-l border-app-accent/30 pl-3 md:pl-4 py-0.5 md:py-1">
                      <p className="text-[10px] md:text-xs leading-relaxed text-app-muted italic">
                        {item.note}
                      </p>
                    </div>
                  )}
                </div>
              </button>
            ))}
          </div>

          <div className="flex flex-col md:flex-row gap-4 md:gap-6 pt-8 md:pt-12">
            <button 
              onClick={nextStep}
              className="md:order-2 flex-[2] btn-primary flex items-center justify-center gap-2"
            >
              {currentStep === CATEGORIES.length - 1 ? 'Finalizar e Ver Lista' : 'Continuar para Próxima'} <ChevronRight size={14} />
            </button>
            {currentStep > 0 && (
              <button 
                onClick={prevStep}
                className="md:order-1 flex-1 btn-secondary flex items-center justify-center gap-2"
              >
                <ChevronLeft size={14} /> Anterior
              </button>
            )}
          </div>
        </div>
      </section>
    </div>
  );
};

const MyListPage: React.FC = () => {
  const { selectedItems, clearAllData } = useExamination();
  const navigate = useNavigate();

  const selectedSins = EXAMINATION_ITEMS.filter(item => selectedItems.includes(item.id));

  return (
    <div className="flex-1 flex flex-col md:grid md:grid-cols-12 gap-0 h-full overflow-hidden">
      <aside className="md:col-span-4 bg-app-aside p-6 md:p-12 overflow-y-auto border-b md:border-b-0 md:border-r border-app-border min-h-0">
        <div className="flex flex-col border border-app-border bg-white p-6 md:p-10 h-auto md:h-full">
          <p className="text-[9px] md:text-[10px] font-sans uppercase tracking-[0.2em] text-app-muted mb-2">Recolhimento</p>
          <h3 className="text-xl md:text-2xl font-light italic mb-6 md:mb-8 text-app-ink">Lista para Confissão</h3>
          
          <div className="flex-1 space-y-4 md:space-y-8 overflow-y-auto mb-6 md:mb-0">
            {selectedSins.length === 0 ? (
              <p className="text-xs md:text-sm text-app-muted italic text-center py-6 md:py-12">Nenhum pecado marcado.</p>
            ) : (
              selectedSins.map((item, idx) => (
                <div key={item.id} className="border-l-2 border-app-accent pl-4 md:pl-5 py-0.5 md:py-1">
                  <p className="text-[11px] md:text-xs leading-relaxed text-app-secondary font-serif italic">“{item.text}”</p>
                </div>
              ))
            )}
          </div>
          
          <div className="pt-6 md:pt-8 border-t border-app-border flex flex-col space-y-4 shrink-0">
            <button 
              onClick={() => {
                if(confirm('Apagar todos os dados marcados?')) {
                  clearAllData();
                  navigate('/');
                }
              }}
              className="btn-primary bg-red-900 border-red-900 hover:bg-red-950 py-3"
            >
              <Trash2 size={12} className="inline mr-2" /> Apagar Dados
            </button>
            <p className="text-[8px] md:text-[9px] font-sans text-center text-app-muted italic uppercase tracking-widest leading-relaxed">
              Privacidade Absoluta. Dados locais.
            </p>
          </div>
        </div>
      </aside>

      <section className="md:col-span-8 p-6 md:p-16 overflow-y-auto bg-white">
        <div className="max-w-2xl mx-auto space-y-12 md:space-y-16">
          <header className="space-y-3 border-b border-app-border pb-8 md:pb-10">
            <h2 className="text-3xl md:text-4xl font-light italic">Orações Finais</h2>
            <p className="text-app-muted text-[10px] md:text-xs uppercase tracking-[0.2em] leading-relaxed">
              Termine seu exame com estas orações e dirija-se ao confessionário com confiança.
            </p>
          </header>

          <PrayerSection {...PRAYERS.ACUSACAO} />
          <PrayerSection {...PRAYERS.CONTRICAO} />
          <PrayerSection {...PRAYERS.NOSSA_SENHORA} />
          
          <div className="pt-8 mb-12 md:mb-0">
            <button 
              onClick={() => navigate('/exame')}
              className="btn-secondary w-full md:w-auto px-12 block mx-auto py-4"
            >
              Retornar ao Exame
            </button>
          </div>
        </div>
      </section>
    </div>
  );
};

const AboutPage: React.FC = () => {
  const { isLocked, setIsLocked, clearAllData } = useExamination();

  return (
    <div className="flex-1 flex flex-col md:grid md:grid-cols-12 gap-0 h-full overflow-hidden">
      <aside className="md:col-span-4 border-b md:border-b-0 md:border-r border-app-border bg-app-aside p-8 md:p-12 flex flex-col justify-center gap-8">
        <div className="w-10 h-10 md:w-12 md:h-12 border border-app-accent flex items-center justify-center transform rotate-45 mx-auto md:mx-0">
          <div className="transform -rotate-45 text-app-accent text-lg font-serif">†</div>
        </div>
        <div className="text-center md:text-left">
          <h2 className="text-3xl md:text-4xl font-light italic mb-6">Propósito Pastoral</h2>
          <div className="bg-white p-6 md:p-8 border border-app-border space-y-4">
             <p className="text-[9px] md:text-[10px] font-sans text-app-muted uppercase tracking-[0.2em]">Crédito</p>
             <p className="text-lg md:text-xl font-serif italic text-app-ink">
              “Texto original do exame de consciência: Arautos do Evangelho”
             </p>
          </div>
        </div>
      </aside>

      <section className="md:col-span-8 p-6 md:p-16 overflow-y-auto bg-white">
        <div className="max-w-2xl mx-auto space-y-10 md:space-y-12">
          <header className="border-b border-app-border pb-6 md:pb-8">
            <h3 className="text-2xl md:text-3xl font-light italic mb-4">Sobre o Confessio AE</h3>
            <p className="text-app-secondary text-xs md:text-sm leading-relaxed">
              Este aplicativo foi desenvolvido para auxiliar os fiéis católicos na preparação para o 
              Sacramento da Confissão, transformando o tradicional exame de consciência em uma ferramenta 
              digital discreta e reverente.
            </p>
          </header>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-8 md:gap-12">
            <div className="space-y-6">
              <h4 className="text-[10px] md:text-xs uppercase tracking-[0.2em] font-bold text-app-muted border-l-2 border-app-accent pl-3">Identidade</h4>
              <p className="text-[13px] md:text-sm text-app-secondary leading-relaxed">
                App sem fins lucrativos desenvolvido para auxiliar fiéis na preparação para o Sacramento da Confissão.
              </p>
              <p className="text-[13px] md:text-sm text-app-secondary leading-relaxed">
                Desenvolvido inicialmente para a comunidade dos Arautos do Evangelho.
              </p>
            </div>

            <div className="space-y-6">
              <h4 className="text-[10px] md:text-xs uppercase tracking-[0.2em] font-bold text-app-muted flex items-center gap-2 border-l-2 border-app-accent pl-3">
                <Shield size={14} className="text-app-accent" /> Segurança
              </h4>
              <ul className="text-[13px] md:text-sm text-app-secondary space-y-2">
                <li className="flex items-center gap-2"><Check size={12} className="text-app-accent" /> 100% Offline</li>
                <li className="flex items-center gap-2"><Check size={12} className="text-app-accent" /> Sem Servidores</li>
                <li className="flex items-center gap-2"><Check size={12} className="text-app-accent" /> Sem Anúncios</li>
                <li className="flex items-center gap-2"><Check size={12} className="text-app-accent" /> Sem Rastreamento</li>
              </ul>
            </div>
          </div>

          <div className="pt-8 border-t border-app-border space-y-8 pb-12">
            <div className="flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
              <div className="space-y-1">
                <p className="font-bold flex items-center gap-2 text-xs md:text-sm uppercase tracking-widest"><Lock size={14} /> Bloqueio de Privacidade</p>
                <p className="text-[9px] md:text-[10px] text-app-muted italic">Simula biometria/PIN em dispositivos suportados.</p>
              </div>
              <button 
                onClick={() => setIsLocked(!isLocked)}
                className={cn(
                  "w-12 h-6 rounded-none border border-app-ink relative transition-colors duration-200",
                  isLocked ? "bg-app-ink" : "bg-app-bg"
                )}
              >
                <div className={cn(
                  "absolute top-0.5 w-4 h-4 border border-app-ink bg-white transition-all duration-200",
                  isLocked ? "left-7 bg-app-accent border-app-accent" : "left-0.5"
                )} />
              </button>
            </div>

            <button 
              onClick={() => {
                if(confirm('Redefinir aplicativo e apagar todos os dados?')) {
                  clearAllData();
                  window.location.href = '/';
                }
              }}
              className="btn-secondary text-red-800 border-red-200 hover:bg-red-50 hover:text-red-900 w-full md:w-auto px-8 py-3"
            >
              Restaurar Configurações Originais
            </button>
          </div>
        </div>
      </section>
    </div>
  );
};

export default function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/exame" element={<ExaminationPage />} />
          <Route path="/lista" element={<MyListPage />} />
          <Route path="/sobre" element={<AboutPage />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}
