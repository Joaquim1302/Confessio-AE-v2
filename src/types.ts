export interface ExaminationItem {
  id: string;
  text: string;
  category: 'deus' | 'proximo' | 'consigo';
  note?: string;
}

export interface Category {
  id: ExaminationItem['category'];
  title: string;
  description: string;
}
