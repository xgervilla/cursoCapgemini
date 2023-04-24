export function titleCase(str) {
    if(typeof(str) !== 'string') return str
    
    return str?.toLowerCase()
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
}