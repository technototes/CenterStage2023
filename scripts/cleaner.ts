const replacements = ['?', '*', ':', ';', '/', '\\', '|', '<', '>', "'", '"', ' '];

export function Clean(file: string): string {
  let res = file;
  for (const i of replacements) {
    res = res.replaceAll(i, '_');
  }
  // Yoink trailng periods
  res = res.replace(/^\.*/, '')
  // Finally, yoink diacriticals:
  res = res.normalize("NFD").replace(/\p{Diacritic}/gu, "");
  // And trim initial/trailing underscores:
  return res.replace(/^_*/,'').replace(/_*$/,'');
}