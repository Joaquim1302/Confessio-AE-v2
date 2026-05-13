import { useState, useEffect } from 'react';

export function useLocalStorage<T>(key: string, initialValue: T) {
  const [storedValue, setStoredValue] = useState<T>(() => {
    try {
      const item = window.localStorage.getItem(key);
      return item ? JSON.parse(item) : initialValue;
    } catch (error) {
      console.error(error);
      return initialValue;
    }
  });

  useEffect(() => {
    try {
      window.localStorage.setItem(key, JSON.stringify(storedValue));
    } catch (error) {
      console.error(error);
    }
  }, [key, storedValue]);

  return [storedValue, setStoredValue] as const;
}

export function useExamination() {
  const [selectedItems, setSelectedItems] = useLocalStorage<string[]>('selected_sins', []);
  const [isLocked, setIsLocked] = useLocalStorage<boolean>('is_locked', false);
  const [pin, setPin] = useLocalStorage<string | null>('lock_pin', null);

  const toggleItem = (id: string) => {
    setSelectedItems(prev => 
      prev.includes(id) ? prev.filter(i => i !== id) : [...prev, id]
    );
  };

  const clearAllData = () => {
    setSelectedItems([]);
    setIsLocked(false);
    setPin(null);
    window.localStorage.clear();
  };

  return {
    selectedItems,
    toggleItem,
    clearAllData,
    isLocked,
    setIsLocked,
    pin,
    setPin
  };
}
